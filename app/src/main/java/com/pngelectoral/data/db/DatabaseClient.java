package com.pngelectoral.data.db;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseClient {

    private Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static DatabaseClient mInstance;
    //our app database object
    private AppDatabase appDatabase;

    private DatabaseClient(Context mContext) {
        this.mContext = mContext;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mContext, AppDatabase.class, "PNGElectoral")
                .addMigrations(MIGRATION_1_2).addMigrations(MIGRATION_2_3).addMigrations(MIGRATION_3_4).addMigrations(MIGRATION_4_5).build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE IF NOT EXISTS `DpoList` (`userId` INTEGER, firstName TEXT," +
//                    "lastName TEXT,emailAddress TEXT,region INTEGER, regionName TEXT, province INTEGER, provinceName TEXT," +
//                    "district INTEGER, districtName TEXT,llg INTEGER, llgName TEXT,ward INTEGER, wardName TEXT," +
//                    "location INTEGER, locationName TEXT,workingRollLLGId INTEGER, workingRollLLGName TEXT," +
//                    "workingRollWardId INTEGER, workingRollWardName TEXT," +
//                    "PRIMARY KEY(`userId`))");

            database.execSQL("ALTER TABLE voterslist "
                    + " ADD COLUMN OldLocationId INTEGER");
        }
    };

    /** Added migration of database from version 2 to 3*/

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            /*Added table to store data of MKA geo structure*/
            database.execSQL("CREATE TABLE IF NOT EXISTS 'mkageolist' ('actionBy' INTEGER, districtId INTEGER PRIMARY KEY," +
                    "'districtName' TEXT, 'llGs' TEXT)");
            /*Added table to store data of MKA clan*/
            database.execSQL("CREATE TABLE IF NOT EXISTS 'mkaclanlist' ('clanId' INTEGER PRIMARY KEY, clanName TEXT," +
                    "'description' TEXT, 'responseCode' INTEGER)");

            /* Added MKA geo structure data columns in voterlist table*/
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkallgId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkallgName TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaWardId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaWardName TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaLocationId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaLocationName TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaClanId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaClanName TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN enrollFormType TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN transferOutMkaDistrictId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN transferOutMkaLlgId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN transferOutMkaWardId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN transferOutMkaLocationId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN transferInMkaDistrictId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN transferInMkaLlgId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN transferInMkaWardId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN transferInMkaLocationId INTEGER");

            /* Added MKA geo structure data columns in dpolist table*/
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaDistrict INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaDistrictName TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkallg INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkallgName TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaWard INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaWardName TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaLocation INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN mkaLocationName TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN workingRollMKALLGId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN workingRollMKALLGName TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN workingRollMKAWardId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN workingRollMKAWardName TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN workingRollMKALocationId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN workingRollMKALocationName TEXT");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE voterslist ADD COLUMN clanId INTEGER");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN clanName TEXT");

            /*Added table to store data of MKA geo structure*/
            database.execSQL("CREATE TABLE IF NOT EXISTS 'clanlist' ('wardId' INTEGER, 'clanId' INTEGER PRIMARY KEY," +
                    "'clanName' TEXT, 'description' TEXT)");
        }
    };

    /** Phase 1 & 2: Migration for uploadId, sevisPassId, sevisPassStatus */
    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE voterslist ADD COLUMN uploadId TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN sevisPassId TEXT");
            database.execSQL("ALTER TABLE voterslist ADD COLUMN sevisPassStatus TEXT");
        }
    };

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
