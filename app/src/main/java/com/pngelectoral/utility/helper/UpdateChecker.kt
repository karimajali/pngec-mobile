package com.pngelectoral.utility.helper

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import com.pngelectoral.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Phase 1 In-App Auto-Update Checker for PNGEC Mobile.
 * Queries manifest.json on the server, compares versionCode, and performs in-place APK installation.
 */
object UpdateChecker {

    suspend fun checkForAppUpdate(context: Context, isManualCheck: Boolean = false) = withContext(Dispatchers.IO) {
        try {
            val manifestUrlStr = BuildConfig.BASE_URL.replace("/api/mobile/", "/manifest.json")
            val url = URL(manifestUrlStr)
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.requestMethod = "GET"

            if (connection.responseCode == 200) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val responseStr = reader.readText()
                reader.close()

                val rootJson = JSONObject(responseStr)
                if (rootJson.has("mobile")) {
                    val mobileJson = rootJson.getJSONObject("mobile")
                    val serverVersionCode = mobileJson.optInt("versionCode", 0)
                    val serverVersionName = mobileJson.optString("versionName", "")
                    val downloadUrl = mobileJson.optString("downloadUrl", "")
                    val releaseNotes = mobileJson.optString("releaseNotes", "")

                    if (serverVersionCode > BuildConfig.VERSION_CODE) {
                        withContext(Dispatchers.Main) {
                            showUpdateDialog(context, serverVersionName, downloadUrl, releaseNotes)
                        }
                    } else if (isManualCheck) {
                        withContext(Dispatchers.Main) {
                            AlertDialog.Builder(context)
                                .setTitle("PNGEC Up to Date")
                                .setMessage("You are running the latest app version (${BuildConfig.VERSION_NAME}).")
                                .setPositiveButton("OK", null)
                                .show()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            // Fail silently on network errors
        }
    }

    private fun showUpdateDialog(context: Context, versionName: String, downloadUrl: String, releaseNotes: String) {
        AlertDialog.Builder(context)
            .setTitle("New Update Available ($versionName)")
            .setMessage("A new version of PNGEC Mobile is available.\n\nChanges:\n$releaseNotes\n\nWould you like to update now?")
            .setPositiveButton("Update Now") { _, _ ->
                downloadAndInstallApk(context, downloadUrl)
            }
            .setNegativeButton("Later", null)
            .setCancelable(false)
            .show()
    }

    private fun downloadAndInstallApk(context: Context, downloadUrl: String) {
        try {
            val destinationFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "PNGEC-update.apk")
            if (destinationFile.exists()) destinationFile.delete()

            val request = DownloadManager.Request(Uri.parse(downloadUrl))
                .setTitle("Downloading PNGEC Update")
                .setDescription("Downloading latest application package...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.fromFile(destinationFile))
                .setAllowedOverMetered(true)

            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = downloadManager.enqueue(request)

            val onComplete = object : BroadcastReceiver() {
                override fun onReceive(ctxt: Context?, intent: Intent?) {
                    val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L) ?: -1L
                    if (id == downloadId) {
                        context.unregisterReceiver(this)
                        installApk(context, destinationFile)
                    }
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_EXPORTED)
            } else {
                context.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun installApk(context: Context, apkFile: File) {
        try {
            val contentUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                apkFile
            )

            val installIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(contentUri, "application/vnd.android.package-archive")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            context.startActivity(installIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
