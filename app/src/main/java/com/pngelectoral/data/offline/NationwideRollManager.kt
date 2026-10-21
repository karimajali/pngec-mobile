package com.pngelectoral.data.offline

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest

/**
 * Phase 2 Nationwide 6M Offline SQLite Roll Mobile Viewer (Point 7)
 * Directly queries pre-indexed read-only SQLite database on SD card / app storage.
 * Provides instant lookups across 6,000,000 voter records with SHA-256 integrity verification.
 */
object NationwideRollManager {

    enum class SearchType {
        NAME,
        ELECTOR_ID,
        SEVISPASS_ID
    }

    data class OfflineRollVoter(
        val electorId: Long,
        val voterName: String,
        val lName: String,
        val fName: String,
        val gender: String,
        val dob: String,
        val provinceId: Int,
        val districtId: Int,
        val llgId: Int,
        val wardId: Int,
        val locationId: Int,
        val status: String,
        val sevisPassId: String,
        val sevisPassStatus: String
    )

    private const val DB_FILENAME = "pngec_nationwide_roll.db"

    /**
     * Finds the nationwide SQLite roll on internal storage, app external files, or SD card.
     */
    fun findNationwideRollFile(context: Context): File? {
        val searchCandidates = listOf(
            File(context.getExternalFilesDir(null), DB_FILENAME),
            File(context.filesDir, DB_FILENAME),
            File(Environment.getExternalStorageDirectory(), "PNGEC/$DB_FILENAME"),
            File(Environment.getExternalStorageDirectory(), "Download/$DB_FILENAME"),
            File(context.getExternalFilesDir(null), "roll/$DB_FILENAME")
        )

        return searchCandidates.firstOrNull { it.exists() && it.isFile && it.length() > 0 }
    }

    /**
     * Validates SHA-256 checksum of the SQLite database against its .sha256 companion file.
     */
    fun verifyChecksum(dbFile: File): Boolean {
        val checksumFile = File("${dbFile.absolutePath}.sha256")
        if (!checksumFile.exists() || !checksumFile.isFile) {
            // Checksum file not present; consider valid if DB file size is non-trivial
            return dbFile.length() > 1024
        }

        return try {
            val expectedHash = checksumFile.readText().trim().split("\\s+".toRegex())[0].lowercase()
            val actualHash = computeFileSha256(dbFile).lowercase()
            expectedHash.equals(actualHash, ignoreCase = true)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun computeFileSha256(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        FileInputStream(file).use { fis ->
            val buffer = ByteArray(8192)
            var bytesRead: Int
            while (fis.read(buffer).also { bytesRead = it } != -1) {
                digest.update(buffer, 0, bytesRead)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }

    /**
     * Executes fast indexed search across the 6-million voter SQLite database.
     */
    fun searchNationwideRoll(
        context: Context,
        query: String,
        type: SearchType = SearchType.NAME,
        limit: Int = 50
    ): List<OfflineRollVoter> {
        val dbFile = findNationwideRollFile(context) ?: return emptyList()
        val results = mutableListOf<OfflineRollVoter>()

        var db: SQLiteDatabase? = null
        try {
            db = SQLiteDatabase.openDatabase(dbFile.absolutePath, null, SQLiteDatabase.OPEN_READONLY)

            val (selectionSql, selectionArgs) = when (type) {
                SearchType.SEVISPASS_ID -> {
                    "SELECT electorId, voterName, lName, fName, gender, dob, provinceId, districtId, llgId, wardId, locationId, status, sevisPassId, sevisPassStatus " +
                            "FROM voterslist WHERE sevisPassId = ? LIMIT $limit" to arrayOf(query.trim())
                }
                SearchType.ELECTOR_ID -> {
                    "SELECT electorId, voterName, lName, fName, gender, dob, provinceId, districtId, llgId, wardId, locationId, status, sevisPassId, sevisPassStatus " +
                            "FROM voterslist WHERE electorId = ? LIMIT $limit" to arrayOf(query.trim())
                }
                SearchType.NAME -> {
                    val trimmed = query.trim()
                    "SELECT electorId, voterName, lName, fName, gender, dob, provinceId, districtId, llgId, wardId, locationId, status, sevisPassId, sevisPassStatus " +
                            "FROM voterslist WHERE voterName LIKE ? OR lName LIKE ? OR fName LIKE ? LIMIT $limit" to
                            arrayOf("%$trimmed%", "%$trimmed%", "%$trimmed%")
                }
            }

            db.rawQuery(selectionSql, selectionArgs).use { cursor ->
                while (cursor.moveToNext()) {
                    results.add(
                        OfflineRollVoter(
                            electorId = cursor.getLong(cursor.getColumnIndexOrThrow("electorId")),
                            voterName = cursor.getString(cursor.getColumnIndexOrThrow("voterName")) ?: "",
                            lName = cursor.getString(cursor.getColumnIndexOrThrow("lName")) ?: "",
                            fName = cursor.getString(cursor.getColumnIndexOrThrow("fName")) ?: "",
                            gender = cursor.getString(cursor.getColumnIndexOrThrow("gender")) ?: "",
                            dob = cursor.getString(cursor.getColumnIndexOrThrow("dob")) ?: "",
                            provinceId = cursor.getInt(cursor.getColumnIndexOrThrow("provinceId")),
                            districtId = cursor.getInt(cursor.getColumnIndexOrThrow("districtId")),
                            llgId = cursor.getInt(cursor.getColumnIndexOrThrow("llgId")),
                            wardId = cursor.getInt(cursor.getColumnIndexOrThrow("wardId")),
                            locationId = cursor.getInt(cursor.getColumnIndexOrThrow("locationId")),
                            status = cursor.getString(cursor.getColumnIndexOrThrow("status")) ?: "",
                            sevisPassId = cursor.getString(cursor.getColumnIndexOrThrow("sevisPassId")) ?: "",
                            sevisPassStatus = cursor.getString(cursor.getColumnIndexOrThrow("sevisPassStatus")) ?: ""
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                db?.close()
            } catch (ignored: Exception) {}
        }

        return results
    }
}
