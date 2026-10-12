package com.pngelectoral.utility

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.PrintWriter

class ShareUtils {
    companion object {

        fun extractDatabaseAndShare(application: Application,onFile: (File?)->Unit) {
            try {
                val file = File(application.cacheDir, "PNGElectoral")
                if (!file.exists())
                    file.mkdirs()

                val data = Environment.getDataDirectory()
                val databaseName = "PNGElectoral"
                val listOfFiles = arrayListOf<File>()
                listOfFiles.add(
                    File(
                        data,
                        "//data//$application.packageName//databases//$databaseName"
                    )
                )
                listOfFiles.add(
                    File(
                        data,
                        "//data//$application.packageName//databases//$databaseName-shm"
                    )
                )
                listOfFiles.add(
                    File(
                        data,
                        "//data//$application.packageName//databases//$databaseName-wal"
                    )
                )

                val zipFile = File(
                    file,
                    "${application.applicationInfo.loadLabel(application.packageManager)}_Log_.zip"
                )
                FileUtils.zip(listOfFiles, zipFile.absolutePath)
                onFile(zipFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun shareFile(application: Application, exportDatabaseFile: File?) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "application/octet-stream"
            val dbUri = FileProvider.getUriForFile(
                application,
                "${application.packageName}.com.hb.logger.provider",
                exportDatabaseFile!!
            )

            shareIntent.putExtra(Intent.EXTRA_STREAM, dbUri)
            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                "${application.applicationInfo.loadLabel(application.packageManager)} Log :: "
            )

            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

//            application.startActivity(Intent.createChooser(shareIntent, "Share File Via"))
            val chooserIntent = Intent.createChooser(shareIntent, "Share File Via")
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            application.startActivity(chooserIntent)
        }
    }
}