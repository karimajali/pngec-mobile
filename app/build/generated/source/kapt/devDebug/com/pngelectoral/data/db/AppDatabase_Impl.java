package com.pngelectoral.data.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.pngelectoral.data.dao.ClanListDao;
import com.pngelectoral.data.dao.ClanListDao_Impl;
import com.pngelectoral.data.dao.DistrictListDao;
import com.pngelectoral.data.dao.DistrictListDao_Impl;
import com.pngelectoral.data.dao.DpoListDao;
import com.pngelectoral.data.dao.DpoListDao_Impl;
import com.pngelectoral.data.dao.GeoClanListDao;
import com.pngelectoral.data.dao.GeoClanListDao_Impl;
import com.pngelectoral.data.dao.GeoListDao;
import com.pngelectoral.data.dao.GeoListDao_Impl;
import com.pngelectoral.data.dao.LLGListDao;
import com.pngelectoral.data.dao.LLGListDao_Impl;
import com.pngelectoral.data.dao.LocationListDao;
import com.pngelectoral.data.dao.LocationListDao_Impl;
import com.pngelectoral.data.dao.OccupationListDao;
import com.pngelectoral.data.dao.OccupationListDao_Impl;
import com.pngelectoral.data.dao.ProvinceListDao;
import com.pngelectoral.data.dao.ProvinceListDao_Impl;
import com.pngelectoral.data.dao.RegionListDao;
import com.pngelectoral.data.dao.RegionListDao_Impl;
import com.pngelectoral.data.dao.TransferInVotersListDao;
import com.pngelectoral.data.dao.TransferInVotersListDao_Impl;
import com.pngelectoral.data.dao.VotersListDao;
import com.pngelectoral.data.dao.VotersListDao_Impl;
import com.pngelectoral.data.dao.WardListDao;
import com.pngelectoral.data.dao.WardListDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class AppDatabase_Impl extends AppDatabase {
  private volatile VotersListDao _votersListDao;

  private volatile RegionListDao _regionListDao;

  private volatile ProvinceListDao _provinceListDao;

  private volatile DistrictListDao _districtListDao;

  private volatile LLGListDao _lLGListDao;

  private volatile WardListDao _wardListDao;

  private volatile LocationListDao _locationListDao;

  private volatile OccupationListDao _occupationListDao;

  private volatile TransferInVotersListDao _transferInVotersListDao;

  private volatile DpoListDao _dpoListDao;

  private volatile GeoListDao _geoListDao;

  private volatile ClanListDao _clanListDao;

  private volatile GeoClanListDao _geoClanListDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `VotersList` (`tempElectorId` INTEGER, `electorId` INTEGER, `oldElectorID` INTEGER, `locationId` INTEGER, `OldLocationId` INTEGER, `locationName` TEXT, `wardId` INTEGER, `wardName` TEXT, `llgId` INTEGER, `llgName` TEXT, `districtId` INTEGER, `provinceId` INTEGER, `regionId` INTEGER, `lName` TEXT, `fName` TEXT, `voterName` TEXT, `gender` TEXT, `dob` TEXT, `isYOB` INTEGER, `isProvisional` INTEGER, `isPWD` INTEGER, `religion` TEXT, `fatherLName` TEXT, `fatherFName` TEXT, `motherLName` TEXT, `motherFName` TEXT, `provOfBirthFK` INTEGER, `provinceOfBirth` TEXT, `provinceName` TEXT, `districtName` TEXT, `placeOfBirth` TEXT, `occupationFK` INTEGER, `occupation` TEXT, `clanFK` INTEGER, `clan` TEXT, `subClan` TEXT, `address` TEXT, `natID` TEXT, `natIDRecordNo` TEXT, `status` TEXT, `houseFK` INTEGER, `houseSortOrder` INTEGER, `restHouse` TEXT, `sectionNo` INTEGER, `lotNo` INTEGER, `streetName` TEXT, `suburbTownSettlement` TEXT, `familyFK` INTEGER, `familySortOrder` INTEGER, `familyNumber` INTEGER, `familyName` TEXT, `dpo` TEXT, `lat` REAL, `long` REAL, `posted` TEXT, `updatedDate` TEXT, `comment` TEXT, `electorProfileImage` TEXT, `electorProfileImageUrl` TEXT, `electorFormImage` TEXT, `electorFormImageUrl` TEXT, `electorProfileImageFile` TEXT, `electorFormImageFile` TEXT, `enrolmentFormNumber` TEXT, `createdBy` INTEGER, `isSyncToServer` INTEGER, `isDataUploaded` INTEGER, `isImageUploaded` INTEGER, `updatedDateTime` INTEGER, `syncStatus` INTEGER, `rejectRecordUpdated` INTEGER, `syncDateTime` TEXT, `duplicateId` INTEGER, `transferOutRegionId` INTEGER, `transferOutProvinceId` INTEGER, `transferOutDistrictId` INTEGER, `transferOutLLGId` INTEGER, `transferOutWardId` INTEGER, `transferOutLocationId` INTEGER, `transferInRegionId` INTEGER, `transferInProvinceId` INTEGER, `transferInDistrictId` INTEGER, `transferInLLGId` INTEGER, `transferInWardId` INTEGER, `transferInLocationId` INTEGER, `errorMessage` TEXT, `rejectedReason` TEXT, `isRejected` INTEGER, `isHeader` INTEGER, `mkaDistrictId` INTEGER, `mkaDistrictName` TEXT, `mkallgId` INTEGER, `mkallgName` TEXT, `mkaWardId` INTEGER, `mkaWardName` TEXT, `mkaLocationId` INTEGER, `mkaLocationName` TEXT, `mkaClanId` INTEGER, `mkaClanName` TEXT, `enrollFormType` TEXT, `transferOutMkaDistrictId` INTEGER, `transferOutMkaLlgId` INTEGER, `transferOutMkaWardId` INTEGER, `transferOutMkaLocationId` INTEGER, `transferInMkaDistrictId` INTEGER, `transferInMkaLlgId` INTEGER, `transferInMkaWardId` INTEGER, `transferInMkaLocationId` INTEGER, `clanId` INTEGER, `clanName` TEXT, PRIMARY KEY(`electorId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `RegionList` (`regionId` INTEGER, `regionName` TEXT, `selected` INTEGER, PRIMARY KEY(`regionId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ProvinceList` (`provinceId` INTEGER, `provinceName` TEXT, `regionId` INTEGER, PRIMARY KEY(`provinceId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `DistrictList` (`districtId` INTEGER, `districtName` TEXT, `provinceId` INTEGER, `regionId` INTEGER, PRIMARY KEY(`districtId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `LLGList` (`llgId` INTEGER, `llgName` TEXT, `districtId` INTEGER, `provinceId` INTEGER, `regionId` INTEGER, PRIMARY KEY(`llgId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `WardList` (`wardId` INTEGER, `wardName` TEXT, `wardNumber` INTEGER, `llgId` INTEGER, `districtId` INTEGER, `provinceId` INTEGER, `regionId` INTEGER, PRIMARY KEY(`wardId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `LocationList` (`locationId` INTEGER, `locationName` TEXT, `llgId` INTEGER, `districtId` INTEGER, `provinceId` INTEGER, `regionId` INTEGER, `wardId` INTEGER, PRIMARY KEY(`locationId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `OccupationList` (`occupationPK` INTEGER, `occupationName` TEXT, PRIMARY KEY(`occupationPK`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `DpoList` (`userId` INTEGER, `firstName` TEXT, `lastName` TEXT, `emailAddress` TEXT, `region` INTEGER, `regionName` TEXT, `province` INTEGER, `provinceName` TEXT, `district` INTEGER, `districtName` TEXT, `llg` INTEGER, `llgName` TEXT, `ward` INTEGER, `wardName` TEXT, `location` INTEGER, `locationName` TEXT, `mkaDistrict` INTEGER, `mkaDistrictName` TEXT, `mkallg` INTEGER, `mkallgName` TEXT, `mkaWard` INTEGER, `mkaWardName` TEXT, `mkaLocation` INTEGER, `mkaLocationName` TEXT, `workingRollLLGId` INTEGER, `workingRollLLGName` TEXT, `workingRollWardId` INTEGER, `workingRollWardName` TEXT, `workingRollLocationId` INTEGER, `workingRollLocationName` TEXT, `workingRollMKALLGId` INTEGER, `workingRollMKALLGName` TEXT, `workingRollMKAWardId` INTEGER, `workingRollMKAWardName` TEXT, `workingRollMKALocationId` INTEGER, `workingRollMKALocationName` TEXT, PRIMARY KEY(`userId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `mkageolist` (`actionBy` INTEGER, `districtId` INTEGER, `districtName` TEXT, `llGs` TEXT, PRIMARY KEY(`districtId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `mkaclanlist` (`clanId` INTEGER, `clanName` TEXT, `description` TEXT, `responseCode` INTEGER, PRIMARY KEY(`clanId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `clanlist` (`clanId` INTEGER NOT NULL, `wardId` INTEGER, `clanName` TEXT NOT NULL, `description` TEXT, PRIMARY KEY(`clanId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"122b49c99293028c17b34f25882c0618\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `VotersList`");
        _db.execSQL("DROP TABLE IF EXISTS `RegionList`");
        _db.execSQL("DROP TABLE IF EXISTS `ProvinceList`");
        _db.execSQL("DROP TABLE IF EXISTS `DistrictList`");
        _db.execSQL("DROP TABLE IF EXISTS `LLGList`");
        _db.execSQL("DROP TABLE IF EXISTS `WardList`");
        _db.execSQL("DROP TABLE IF EXISTS `LocationList`");
        _db.execSQL("DROP TABLE IF EXISTS `OccupationList`");
        _db.execSQL("DROP TABLE IF EXISTS `DpoList`");
        _db.execSQL("DROP TABLE IF EXISTS `mkageolist`");
        _db.execSQL("DROP TABLE IF EXISTS `mkaclanlist`");
        _db.execSQL("DROP TABLE IF EXISTS `clanlist`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsVotersList = new HashMap<String, TableInfo.Column>(110);
        _columnsVotersList.put("tempElectorId", new TableInfo.Column("tempElectorId", "INTEGER", false, 0));
        _columnsVotersList.put("electorId", new TableInfo.Column("electorId", "INTEGER", false, 1));
        _columnsVotersList.put("oldElectorID", new TableInfo.Column("oldElectorID", "INTEGER", false, 0));
        _columnsVotersList.put("locationId", new TableInfo.Column("locationId", "INTEGER", false, 0));
        _columnsVotersList.put("OldLocationId", new TableInfo.Column("OldLocationId", "INTEGER", false, 0));
        _columnsVotersList.put("locationName", new TableInfo.Column("locationName", "TEXT", false, 0));
        _columnsVotersList.put("wardId", new TableInfo.Column("wardId", "INTEGER", false, 0));
        _columnsVotersList.put("wardName", new TableInfo.Column("wardName", "TEXT", false, 0));
        _columnsVotersList.put("llgId", new TableInfo.Column("llgId", "INTEGER", false, 0));
        _columnsVotersList.put("llgName", new TableInfo.Column("llgName", "TEXT", false, 0));
        _columnsVotersList.put("districtId", new TableInfo.Column("districtId", "INTEGER", false, 0));
        _columnsVotersList.put("provinceId", new TableInfo.Column("provinceId", "INTEGER", false, 0));
        _columnsVotersList.put("regionId", new TableInfo.Column("regionId", "INTEGER", false, 0));
        _columnsVotersList.put("lName", new TableInfo.Column("lName", "TEXT", false, 0));
        _columnsVotersList.put("fName", new TableInfo.Column("fName", "TEXT", false, 0));
        _columnsVotersList.put("voterName", new TableInfo.Column("voterName", "TEXT", false, 0));
        _columnsVotersList.put("gender", new TableInfo.Column("gender", "TEXT", false, 0));
        _columnsVotersList.put("dob", new TableInfo.Column("dob", "TEXT", false, 0));
        _columnsVotersList.put("isYOB", new TableInfo.Column("isYOB", "INTEGER", false, 0));
        _columnsVotersList.put("isProvisional", new TableInfo.Column("isProvisional", "INTEGER", false, 0));
        _columnsVotersList.put("isPWD", new TableInfo.Column("isPWD", "INTEGER", false, 0));
        _columnsVotersList.put("religion", new TableInfo.Column("religion", "TEXT", false, 0));
        _columnsVotersList.put("fatherLName", new TableInfo.Column("fatherLName", "TEXT", false, 0));
        _columnsVotersList.put("fatherFName", new TableInfo.Column("fatherFName", "TEXT", false, 0));
        _columnsVotersList.put("motherLName", new TableInfo.Column("motherLName", "TEXT", false, 0));
        _columnsVotersList.put("motherFName", new TableInfo.Column("motherFName", "TEXT", false, 0));
        _columnsVotersList.put("provOfBirthFK", new TableInfo.Column("provOfBirthFK", "INTEGER", false, 0));
        _columnsVotersList.put("provinceOfBirth", new TableInfo.Column("provinceOfBirth", "TEXT", false, 0));
        _columnsVotersList.put("provinceName", new TableInfo.Column("provinceName", "TEXT", false, 0));
        _columnsVotersList.put("districtName", new TableInfo.Column("districtName", "TEXT", false, 0));
        _columnsVotersList.put("placeOfBirth", new TableInfo.Column("placeOfBirth", "TEXT", false, 0));
        _columnsVotersList.put("occupationFK", new TableInfo.Column("occupationFK", "INTEGER", false, 0));
        _columnsVotersList.put("occupation", new TableInfo.Column("occupation", "TEXT", false, 0));
        _columnsVotersList.put("clanFK", new TableInfo.Column("clanFK", "INTEGER", false, 0));
        _columnsVotersList.put("clan", new TableInfo.Column("clan", "TEXT", false, 0));
        _columnsVotersList.put("subClan", new TableInfo.Column("subClan", "TEXT", false, 0));
        _columnsVotersList.put("address", new TableInfo.Column("address", "TEXT", false, 0));
        _columnsVotersList.put("natID", new TableInfo.Column("natID", "TEXT", false, 0));
        _columnsVotersList.put("natIDRecordNo", new TableInfo.Column("natIDRecordNo", "TEXT", false, 0));
        _columnsVotersList.put("status", new TableInfo.Column("status", "TEXT", false, 0));
        _columnsVotersList.put("houseFK", new TableInfo.Column("houseFK", "INTEGER", false, 0));
        _columnsVotersList.put("houseSortOrder", new TableInfo.Column("houseSortOrder", "INTEGER", false, 0));
        _columnsVotersList.put("restHouse", new TableInfo.Column("restHouse", "TEXT", false, 0));
        _columnsVotersList.put("sectionNo", new TableInfo.Column("sectionNo", "INTEGER", false, 0));
        _columnsVotersList.put("lotNo", new TableInfo.Column("lotNo", "INTEGER", false, 0));
        _columnsVotersList.put("streetName", new TableInfo.Column("streetName", "TEXT", false, 0));
        _columnsVotersList.put("suburbTownSettlement", new TableInfo.Column("suburbTownSettlement", "TEXT", false, 0));
        _columnsVotersList.put("familyFK", new TableInfo.Column("familyFK", "INTEGER", false, 0));
        _columnsVotersList.put("familySortOrder", new TableInfo.Column("familySortOrder", "INTEGER", false, 0));
        _columnsVotersList.put("familyNumber", new TableInfo.Column("familyNumber", "INTEGER", false, 0));
        _columnsVotersList.put("familyName", new TableInfo.Column("familyName", "TEXT", false, 0));
        _columnsVotersList.put("dpo", new TableInfo.Column("dpo", "TEXT", false, 0));
        _columnsVotersList.put("lat", new TableInfo.Column("lat", "REAL", false, 0));
        _columnsVotersList.put("long", new TableInfo.Column("long", "REAL", false, 0));
        _columnsVotersList.put("posted", new TableInfo.Column("posted", "TEXT", false, 0));
        _columnsVotersList.put("updatedDate", new TableInfo.Column("updatedDate", "TEXT", false, 0));
        _columnsVotersList.put("comment", new TableInfo.Column("comment", "TEXT", false, 0));
        _columnsVotersList.put("electorProfileImage", new TableInfo.Column("electorProfileImage", "TEXT", false, 0));
        _columnsVotersList.put("electorProfileImageUrl", new TableInfo.Column("electorProfileImageUrl", "TEXT", false, 0));
        _columnsVotersList.put("electorFormImage", new TableInfo.Column("electorFormImage", "TEXT", false, 0));
        _columnsVotersList.put("electorFormImageUrl", new TableInfo.Column("electorFormImageUrl", "TEXT", false, 0));
        _columnsVotersList.put("electorProfileImageFile", new TableInfo.Column("electorProfileImageFile", "TEXT", false, 0));
        _columnsVotersList.put("electorFormImageFile", new TableInfo.Column("electorFormImageFile", "TEXT", false, 0));
        _columnsVotersList.put("enrolmentFormNumber", new TableInfo.Column("enrolmentFormNumber", "TEXT", false, 0));
        _columnsVotersList.put("createdBy", new TableInfo.Column("createdBy", "INTEGER", false, 0));
        _columnsVotersList.put("isSyncToServer", new TableInfo.Column("isSyncToServer", "INTEGER", false, 0));
        _columnsVotersList.put("isDataUploaded", new TableInfo.Column("isDataUploaded", "INTEGER", false, 0));
        _columnsVotersList.put("isImageUploaded", new TableInfo.Column("isImageUploaded", "INTEGER", false, 0));
        _columnsVotersList.put("updatedDateTime", new TableInfo.Column("updatedDateTime", "INTEGER", false, 0));
        _columnsVotersList.put("syncStatus", new TableInfo.Column("syncStatus", "INTEGER", false, 0));
        _columnsVotersList.put("rejectRecordUpdated", new TableInfo.Column("rejectRecordUpdated", "INTEGER", false, 0));
        _columnsVotersList.put("syncDateTime", new TableInfo.Column("syncDateTime", "TEXT", false, 0));
        _columnsVotersList.put("duplicateId", new TableInfo.Column("duplicateId", "INTEGER", false, 0));
        _columnsVotersList.put("transferOutRegionId", new TableInfo.Column("transferOutRegionId", "INTEGER", false, 0));
        _columnsVotersList.put("transferOutProvinceId", new TableInfo.Column("transferOutProvinceId", "INTEGER", false, 0));
        _columnsVotersList.put("transferOutDistrictId", new TableInfo.Column("transferOutDistrictId", "INTEGER", false, 0));
        _columnsVotersList.put("transferOutLLGId", new TableInfo.Column("transferOutLLGId", "INTEGER", false, 0));
        _columnsVotersList.put("transferOutWardId", new TableInfo.Column("transferOutWardId", "INTEGER", false, 0));
        _columnsVotersList.put("transferOutLocationId", new TableInfo.Column("transferOutLocationId", "INTEGER", false, 0));
        _columnsVotersList.put("transferInRegionId", new TableInfo.Column("transferInRegionId", "INTEGER", false, 0));
        _columnsVotersList.put("transferInProvinceId", new TableInfo.Column("transferInProvinceId", "INTEGER", false, 0));
        _columnsVotersList.put("transferInDistrictId", new TableInfo.Column("transferInDistrictId", "INTEGER", false, 0));
        _columnsVotersList.put("transferInLLGId", new TableInfo.Column("transferInLLGId", "INTEGER", false, 0));
        _columnsVotersList.put("transferInWardId", new TableInfo.Column("transferInWardId", "INTEGER", false, 0));
        _columnsVotersList.put("transferInLocationId", new TableInfo.Column("transferInLocationId", "INTEGER", false, 0));
        _columnsVotersList.put("errorMessage", new TableInfo.Column("errorMessage", "TEXT", false, 0));
        _columnsVotersList.put("rejectedReason", new TableInfo.Column("rejectedReason", "TEXT", false, 0));
        _columnsVotersList.put("isRejected", new TableInfo.Column("isRejected", "INTEGER", false, 0));
        _columnsVotersList.put("isHeader", new TableInfo.Column("isHeader", "INTEGER", false, 0));
        _columnsVotersList.put("mkaDistrictId", new TableInfo.Column("mkaDistrictId", "INTEGER", false, 0));
        _columnsVotersList.put("mkaDistrictName", new TableInfo.Column("mkaDistrictName", "TEXT", false, 0));
        _columnsVotersList.put("mkallgId", new TableInfo.Column("mkallgId", "INTEGER", false, 0));
        _columnsVotersList.put("mkallgName", new TableInfo.Column("mkallgName", "TEXT", false, 0));
        _columnsVotersList.put("mkaWardId", new TableInfo.Column("mkaWardId", "INTEGER", false, 0));
        _columnsVotersList.put("mkaWardName", new TableInfo.Column("mkaWardName", "TEXT", false, 0));
        _columnsVotersList.put("mkaLocationId", new TableInfo.Column("mkaLocationId", "INTEGER", false, 0));
        _columnsVotersList.put("mkaLocationName", new TableInfo.Column("mkaLocationName", "TEXT", false, 0));
        _columnsVotersList.put("mkaClanId", new TableInfo.Column("mkaClanId", "INTEGER", false, 0));
        _columnsVotersList.put("mkaClanName", new TableInfo.Column("mkaClanName", "TEXT", false, 0));
        _columnsVotersList.put("enrollFormType", new TableInfo.Column("enrollFormType", "TEXT", false, 0));
        _columnsVotersList.put("transferOutMkaDistrictId", new TableInfo.Column("transferOutMkaDistrictId", "INTEGER", false, 0));
        _columnsVotersList.put("transferOutMkaLlgId", new TableInfo.Column("transferOutMkaLlgId", "INTEGER", false, 0));
        _columnsVotersList.put("transferOutMkaWardId", new TableInfo.Column("transferOutMkaWardId", "INTEGER", false, 0));
        _columnsVotersList.put("transferOutMkaLocationId", new TableInfo.Column("transferOutMkaLocationId", "INTEGER", false, 0));
        _columnsVotersList.put("transferInMkaDistrictId", new TableInfo.Column("transferInMkaDistrictId", "INTEGER", false, 0));
        _columnsVotersList.put("transferInMkaLlgId", new TableInfo.Column("transferInMkaLlgId", "INTEGER", false, 0));
        _columnsVotersList.put("transferInMkaWardId", new TableInfo.Column("transferInMkaWardId", "INTEGER", false, 0));
        _columnsVotersList.put("transferInMkaLocationId", new TableInfo.Column("transferInMkaLocationId", "INTEGER", false, 0));
        _columnsVotersList.put("clanId", new TableInfo.Column("clanId", "INTEGER", false, 0));
        _columnsVotersList.put("clanName", new TableInfo.Column("clanName", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVotersList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVotersList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVotersList = new TableInfo("VotersList", _columnsVotersList, _foreignKeysVotersList, _indicesVotersList);
        final TableInfo _existingVotersList = TableInfo.read(_db, "VotersList");
        if (! _infoVotersList.equals(_existingVotersList)) {
          throw new IllegalStateException("Migration didn't properly handle VotersList(com.pngelectoral.ui.model.VotersList).\n"
                  + " Expected:\n" + _infoVotersList + "\n"
                  + " Found:\n" + _existingVotersList);
        }
        final HashMap<String, TableInfo.Column> _columnsRegionList = new HashMap<String, TableInfo.Column>(3);
        _columnsRegionList.put("regionId", new TableInfo.Column("regionId", "INTEGER", false, 1));
        _columnsRegionList.put("regionName", new TableInfo.Column("regionName", "TEXT", false, 0));
        _columnsRegionList.put("selected", new TableInfo.Column("selected", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRegionList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRegionList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRegionList = new TableInfo("RegionList", _columnsRegionList, _foreignKeysRegionList, _indicesRegionList);
        final TableInfo _existingRegionList = TableInfo.read(_db, "RegionList");
        if (! _infoRegionList.equals(_existingRegionList)) {
          throw new IllegalStateException("Migration didn't properly handle RegionList(com.pngelectoral.ui.model.RegionList).\n"
                  + " Expected:\n" + _infoRegionList + "\n"
                  + " Found:\n" + _existingRegionList);
        }
        final HashMap<String, TableInfo.Column> _columnsProvinceList = new HashMap<String, TableInfo.Column>(3);
        _columnsProvinceList.put("provinceId", new TableInfo.Column("provinceId", "INTEGER", false, 1));
        _columnsProvinceList.put("provinceName", new TableInfo.Column("provinceName", "TEXT", false, 0));
        _columnsProvinceList.put("regionId", new TableInfo.Column("regionId", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProvinceList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProvinceList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProvinceList = new TableInfo("ProvinceList", _columnsProvinceList, _foreignKeysProvinceList, _indicesProvinceList);
        final TableInfo _existingProvinceList = TableInfo.read(_db, "ProvinceList");
        if (! _infoProvinceList.equals(_existingProvinceList)) {
          throw new IllegalStateException("Migration didn't properly handle ProvinceList(com.pngelectoral.ui.model.ProvinceList).\n"
                  + " Expected:\n" + _infoProvinceList + "\n"
                  + " Found:\n" + _existingProvinceList);
        }
        final HashMap<String, TableInfo.Column> _columnsDistrictList = new HashMap<String, TableInfo.Column>(4);
        _columnsDistrictList.put("districtId", new TableInfo.Column("districtId", "INTEGER", false, 1));
        _columnsDistrictList.put("districtName", new TableInfo.Column("districtName", "TEXT", false, 0));
        _columnsDistrictList.put("provinceId", new TableInfo.Column("provinceId", "INTEGER", false, 0));
        _columnsDistrictList.put("regionId", new TableInfo.Column("regionId", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDistrictList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDistrictList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDistrictList = new TableInfo("DistrictList", _columnsDistrictList, _foreignKeysDistrictList, _indicesDistrictList);
        final TableInfo _existingDistrictList = TableInfo.read(_db, "DistrictList");
        if (! _infoDistrictList.equals(_existingDistrictList)) {
          throw new IllegalStateException("Migration didn't properly handle DistrictList(com.pngelectoral.ui.model.DistrictList).\n"
                  + " Expected:\n" + _infoDistrictList + "\n"
                  + " Found:\n" + _existingDistrictList);
        }
        final HashMap<String, TableInfo.Column> _columnsLLGList = new HashMap<String, TableInfo.Column>(5);
        _columnsLLGList.put("llgId", new TableInfo.Column("llgId", "INTEGER", false, 1));
        _columnsLLGList.put("llgName", new TableInfo.Column("llgName", "TEXT", false, 0));
        _columnsLLGList.put("districtId", new TableInfo.Column("districtId", "INTEGER", false, 0));
        _columnsLLGList.put("provinceId", new TableInfo.Column("provinceId", "INTEGER", false, 0));
        _columnsLLGList.put("regionId", new TableInfo.Column("regionId", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLLGList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLLGList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLLGList = new TableInfo("LLGList", _columnsLLGList, _foreignKeysLLGList, _indicesLLGList);
        final TableInfo _existingLLGList = TableInfo.read(_db, "LLGList");
        if (! _infoLLGList.equals(_existingLLGList)) {
          throw new IllegalStateException("Migration didn't properly handle LLGList(com.pngelectoral.ui.model.LLGList).\n"
                  + " Expected:\n" + _infoLLGList + "\n"
                  + " Found:\n" + _existingLLGList);
        }
        final HashMap<String, TableInfo.Column> _columnsWardList = new HashMap<String, TableInfo.Column>(7);
        _columnsWardList.put("wardId", new TableInfo.Column("wardId", "INTEGER", false, 1));
        _columnsWardList.put("wardName", new TableInfo.Column("wardName", "TEXT", false, 0));
        _columnsWardList.put("wardNumber", new TableInfo.Column("wardNumber", "INTEGER", false, 0));
        _columnsWardList.put("llgId", new TableInfo.Column("llgId", "INTEGER", false, 0));
        _columnsWardList.put("districtId", new TableInfo.Column("districtId", "INTEGER", false, 0));
        _columnsWardList.put("provinceId", new TableInfo.Column("provinceId", "INTEGER", false, 0));
        _columnsWardList.put("regionId", new TableInfo.Column("regionId", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWardList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWardList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWardList = new TableInfo("WardList", _columnsWardList, _foreignKeysWardList, _indicesWardList);
        final TableInfo _existingWardList = TableInfo.read(_db, "WardList");
        if (! _infoWardList.equals(_existingWardList)) {
          throw new IllegalStateException("Migration didn't properly handle WardList(com.pngelectoral.ui.model.WardList).\n"
                  + " Expected:\n" + _infoWardList + "\n"
                  + " Found:\n" + _existingWardList);
        }
        final HashMap<String, TableInfo.Column> _columnsLocationList = new HashMap<String, TableInfo.Column>(7);
        _columnsLocationList.put("locationId", new TableInfo.Column("locationId", "INTEGER", false, 1));
        _columnsLocationList.put("locationName", new TableInfo.Column("locationName", "TEXT", false, 0));
        _columnsLocationList.put("llgId", new TableInfo.Column("llgId", "INTEGER", false, 0));
        _columnsLocationList.put("districtId", new TableInfo.Column("districtId", "INTEGER", false, 0));
        _columnsLocationList.put("provinceId", new TableInfo.Column("provinceId", "INTEGER", false, 0));
        _columnsLocationList.put("regionId", new TableInfo.Column("regionId", "INTEGER", false, 0));
        _columnsLocationList.put("wardId", new TableInfo.Column("wardId", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLocationList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLocationList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLocationList = new TableInfo("LocationList", _columnsLocationList, _foreignKeysLocationList, _indicesLocationList);
        final TableInfo _existingLocationList = TableInfo.read(_db, "LocationList");
        if (! _infoLocationList.equals(_existingLocationList)) {
          throw new IllegalStateException("Migration didn't properly handle LocationList(com.pngelectoral.ui.model.LocationList).\n"
                  + " Expected:\n" + _infoLocationList + "\n"
                  + " Found:\n" + _existingLocationList);
        }
        final HashMap<String, TableInfo.Column> _columnsOccupationList = new HashMap<String, TableInfo.Column>(2);
        _columnsOccupationList.put("occupationPK", new TableInfo.Column("occupationPK", "INTEGER", false, 1));
        _columnsOccupationList.put("occupationName", new TableInfo.Column("occupationName", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOccupationList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOccupationList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOccupationList = new TableInfo("OccupationList", _columnsOccupationList, _foreignKeysOccupationList, _indicesOccupationList);
        final TableInfo _existingOccupationList = TableInfo.read(_db, "OccupationList");
        if (! _infoOccupationList.equals(_existingOccupationList)) {
          throw new IllegalStateException("Migration didn't properly handle OccupationList(com.pngelectoral.ui.model.OccupationList).\n"
                  + " Expected:\n" + _infoOccupationList + "\n"
                  + " Found:\n" + _existingOccupationList);
        }
        final HashMap<String, TableInfo.Column> _columnsDpoList = new HashMap<String, TableInfo.Column>(36);
        _columnsDpoList.put("userId", new TableInfo.Column("userId", "INTEGER", false, 1));
        _columnsDpoList.put("firstName", new TableInfo.Column("firstName", "TEXT", false, 0));
        _columnsDpoList.put("lastName", new TableInfo.Column("lastName", "TEXT", false, 0));
        _columnsDpoList.put("emailAddress", new TableInfo.Column("emailAddress", "TEXT", false, 0));
        _columnsDpoList.put("region", new TableInfo.Column("region", "INTEGER", false, 0));
        _columnsDpoList.put("regionName", new TableInfo.Column("regionName", "TEXT", false, 0));
        _columnsDpoList.put("province", new TableInfo.Column("province", "INTEGER", false, 0));
        _columnsDpoList.put("provinceName", new TableInfo.Column("provinceName", "TEXT", false, 0));
        _columnsDpoList.put("district", new TableInfo.Column("district", "INTEGER", false, 0));
        _columnsDpoList.put("districtName", new TableInfo.Column("districtName", "TEXT", false, 0));
        _columnsDpoList.put("llg", new TableInfo.Column("llg", "INTEGER", false, 0));
        _columnsDpoList.put("llgName", new TableInfo.Column("llgName", "TEXT", false, 0));
        _columnsDpoList.put("ward", new TableInfo.Column("ward", "INTEGER", false, 0));
        _columnsDpoList.put("wardName", new TableInfo.Column("wardName", "TEXT", false, 0));
        _columnsDpoList.put("location", new TableInfo.Column("location", "INTEGER", false, 0));
        _columnsDpoList.put("locationName", new TableInfo.Column("locationName", "TEXT", false, 0));
        _columnsDpoList.put("mkaDistrict", new TableInfo.Column("mkaDistrict", "INTEGER", false, 0));
        _columnsDpoList.put("mkaDistrictName", new TableInfo.Column("mkaDistrictName", "TEXT", false, 0));
        _columnsDpoList.put("mkallg", new TableInfo.Column("mkallg", "INTEGER", false, 0));
        _columnsDpoList.put("mkallgName", new TableInfo.Column("mkallgName", "TEXT", false, 0));
        _columnsDpoList.put("mkaWard", new TableInfo.Column("mkaWard", "INTEGER", false, 0));
        _columnsDpoList.put("mkaWardName", new TableInfo.Column("mkaWardName", "TEXT", false, 0));
        _columnsDpoList.put("mkaLocation", new TableInfo.Column("mkaLocation", "INTEGER", false, 0));
        _columnsDpoList.put("mkaLocationName", new TableInfo.Column("mkaLocationName", "TEXT", false, 0));
        _columnsDpoList.put("workingRollLLGId", new TableInfo.Column("workingRollLLGId", "INTEGER", false, 0));
        _columnsDpoList.put("workingRollLLGName", new TableInfo.Column("workingRollLLGName", "TEXT", false, 0));
        _columnsDpoList.put("workingRollWardId", new TableInfo.Column("workingRollWardId", "INTEGER", false, 0));
        _columnsDpoList.put("workingRollWardName", new TableInfo.Column("workingRollWardName", "TEXT", false, 0));
        _columnsDpoList.put("workingRollLocationId", new TableInfo.Column("workingRollLocationId", "INTEGER", false, 0));
        _columnsDpoList.put("workingRollLocationName", new TableInfo.Column("workingRollLocationName", "TEXT", false, 0));
        _columnsDpoList.put("workingRollMKALLGId", new TableInfo.Column("workingRollMKALLGId", "INTEGER", false, 0));
        _columnsDpoList.put("workingRollMKALLGName", new TableInfo.Column("workingRollMKALLGName", "TEXT", false, 0));
        _columnsDpoList.put("workingRollMKAWardId", new TableInfo.Column("workingRollMKAWardId", "INTEGER", false, 0));
        _columnsDpoList.put("workingRollMKAWardName", new TableInfo.Column("workingRollMKAWardName", "TEXT", false, 0));
        _columnsDpoList.put("workingRollMKALocationId", new TableInfo.Column("workingRollMKALocationId", "INTEGER", false, 0));
        _columnsDpoList.put("workingRollMKALocationName", new TableInfo.Column("workingRollMKALocationName", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDpoList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDpoList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDpoList = new TableInfo("DpoList", _columnsDpoList, _foreignKeysDpoList, _indicesDpoList);
        final TableInfo _existingDpoList = TableInfo.read(_db, "DpoList");
        if (! _infoDpoList.equals(_existingDpoList)) {
          throw new IllegalStateException("Migration didn't properly handle DpoList(com.pngelectoral.ui.model.DpoList).\n"
                  + " Expected:\n" + _infoDpoList + "\n"
                  + " Found:\n" + _existingDpoList);
        }
        final HashMap<String, TableInfo.Column> _columnsMkageolist = new HashMap<String, TableInfo.Column>(4);
        _columnsMkageolist.put("actionBy", new TableInfo.Column("actionBy", "INTEGER", false, 0));
        _columnsMkageolist.put("districtId", new TableInfo.Column("districtId", "INTEGER", false, 1));
        _columnsMkageolist.put("districtName", new TableInfo.Column("districtName", "TEXT", false, 0));
        _columnsMkageolist.put("llGs", new TableInfo.Column("llGs", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMkageolist = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMkageolist = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMkageolist = new TableInfo("mkageolist", _columnsMkageolist, _foreignKeysMkageolist, _indicesMkageolist);
        final TableInfo _existingMkageolist = TableInfo.read(_db, "mkageolist");
        if (! _infoMkageolist.equals(_existingMkageolist)) {
          throw new IllegalStateException("Migration didn't properly handle mkageolist(com.pngelectoral.ui.model.MKAGeoListData).\n"
                  + " Expected:\n" + _infoMkageolist + "\n"
                  + " Found:\n" + _existingMkageolist);
        }
        final HashMap<String, TableInfo.Column> _columnsMkaclanlist = new HashMap<String, TableInfo.Column>(4);
        _columnsMkaclanlist.put("clanId", new TableInfo.Column("clanId", "INTEGER", false, 1));
        _columnsMkaclanlist.put("clanName", new TableInfo.Column("clanName", "TEXT", false, 0));
        _columnsMkaclanlist.put("description", new TableInfo.Column("description", "TEXT", false, 0));
        _columnsMkaclanlist.put("responseCode", new TableInfo.Column("responseCode", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMkaclanlist = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMkaclanlist = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMkaclanlist = new TableInfo("mkaclanlist", _columnsMkaclanlist, _foreignKeysMkaclanlist, _indicesMkaclanlist);
        final TableInfo _existingMkaclanlist = TableInfo.read(_db, "mkaclanlist");
        if (! _infoMkaclanlist.equals(_existingMkaclanlist)) {
          throw new IllegalStateException("Migration didn't properly handle mkaclanlist(com.pngelectoral.ui.model.MKAClanListData).\n"
                  + " Expected:\n" + _infoMkaclanlist + "\n"
                  + " Found:\n" + _existingMkaclanlist);
        }
        final HashMap<String, TableInfo.Column> _columnsClanlist = new HashMap<String, TableInfo.Column>(4);
        _columnsClanlist.put("clanId", new TableInfo.Column("clanId", "INTEGER", true, 1));
        _columnsClanlist.put("wardId", new TableInfo.Column("wardId", "INTEGER", false, 0));
        _columnsClanlist.put("clanName", new TableInfo.Column("clanName", "TEXT", true, 0));
        _columnsClanlist.put("description", new TableInfo.Column("description", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysClanlist = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesClanlist = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoClanlist = new TableInfo("clanlist", _columnsClanlist, _foreignKeysClanlist, _indicesClanlist);
        final TableInfo _existingClanlist = TableInfo.read(_db, "clanlist");
        if (! _infoClanlist.equals(_existingClanlist)) {
          throw new IllegalStateException("Migration didn't properly handle clanlist(com.pngelectoral.ui.model.ClanListData).\n"
                  + " Expected:\n" + _infoClanlist + "\n"
                  + " Found:\n" + _existingClanlist);
        }
      }
    }, "122b49c99293028c17b34f25882c0618", "4dc189f17a827e659ce4ebb1995734e1");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "VotersList","RegionList","ProvinceList","DistrictList","LLGList","WardList","LocationList","OccupationList","DpoList","mkageolist","mkaclanlist","clanlist");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `VotersList`");
      _db.execSQL("DELETE FROM `RegionList`");
      _db.execSQL("DELETE FROM `ProvinceList`");
      _db.execSQL("DELETE FROM `DistrictList`");
      _db.execSQL("DELETE FROM `LLGList`");
      _db.execSQL("DELETE FROM `WardList`");
      _db.execSQL("DELETE FROM `LocationList`");
      _db.execSQL("DELETE FROM `OccupationList`");
      _db.execSQL("DELETE FROM `DpoList`");
      _db.execSQL("DELETE FROM `mkageolist`");
      _db.execSQL("DELETE FROM `mkaclanlist`");
      _db.execSQL("DELETE FROM `clanlist`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public VotersListDao votersListDao() {
    if (_votersListDao != null) {
      return _votersListDao;
    } else {
      synchronized(this) {
        if(_votersListDao == null) {
          _votersListDao = new VotersListDao_Impl(this);
        }
        return _votersListDao;
      }
    }
  }

  @Override
  public RegionListDao regionListDao() {
    if (_regionListDao != null) {
      return _regionListDao;
    } else {
      synchronized(this) {
        if(_regionListDao == null) {
          _regionListDao = new RegionListDao_Impl(this);
        }
        return _regionListDao;
      }
    }
  }

  @Override
  public ProvinceListDao provinceListDao() {
    if (_provinceListDao != null) {
      return _provinceListDao;
    } else {
      synchronized(this) {
        if(_provinceListDao == null) {
          _provinceListDao = new ProvinceListDao_Impl(this);
        }
        return _provinceListDao;
      }
    }
  }

  @Override
  public DistrictListDao districtListDao() {
    if (_districtListDao != null) {
      return _districtListDao;
    } else {
      synchronized(this) {
        if(_districtListDao == null) {
          _districtListDao = new DistrictListDao_Impl(this);
        }
        return _districtListDao;
      }
    }
  }

  @Override
  public LLGListDao llgListDao() {
    if (_lLGListDao != null) {
      return _lLGListDao;
    } else {
      synchronized(this) {
        if(_lLGListDao == null) {
          _lLGListDao = new LLGListDao_Impl(this);
        }
        return _lLGListDao;
      }
    }
  }

  @Override
  public WardListDao wardListDao() {
    if (_wardListDao != null) {
      return _wardListDao;
    } else {
      synchronized(this) {
        if(_wardListDao == null) {
          _wardListDao = new WardListDao_Impl(this);
        }
        return _wardListDao;
      }
    }
  }

  @Override
  public LocationListDao locationListDao() {
    if (_locationListDao != null) {
      return _locationListDao;
    } else {
      synchronized(this) {
        if(_locationListDao == null) {
          _locationListDao = new LocationListDao_Impl(this);
        }
        return _locationListDao;
      }
    }
  }

  @Override
  public OccupationListDao occupationListDao() {
    if (_occupationListDao != null) {
      return _occupationListDao;
    } else {
      synchronized(this) {
        if(_occupationListDao == null) {
          _occupationListDao = new OccupationListDao_Impl(this);
        }
        return _occupationListDao;
      }
    }
  }

  @Override
  public TransferInVotersListDao transferInVotersListDao() {
    if (_transferInVotersListDao != null) {
      return _transferInVotersListDao;
    } else {
      synchronized(this) {
        if(_transferInVotersListDao == null) {
          _transferInVotersListDao = new TransferInVotersListDao_Impl(this);
        }
        return _transferInVotersListDao;
      }
    }
  }

  @Override
  public DpoListDao dpoListDao() {
    if (_dpoListDao != null) {
      return _dpoListDao;
    } else {
      synchronized(this) {
        if(_dpoListDao == null) {
          _dpoListDao = new DpoListDao_Impl(this);
        }
        return _dpoListDao;
      }
    }
  }

  @Override
  public GeoListDao geoListDao() {
    if (_geoListDao != null) {
      return _geoListDao;
    } else {
      synchronized(this) {
        if(_geoListDao == null) {
          _geoListDao = new GeoListDao_Impl(this);
        }
        return _geoListDao;
      }
    }
  }

  @Override
  public ClanListDao clanListDao() {
    if (_clanListDao != null) {
      return _clanListDao;
    } else {
      synchronized(this) {
        if(_clanListDao == null) {
          _clanListDao = new ClanListDao_Impl(this);
        }
        return _clanListDao;
      }
    }
  }

  @Override
  public GeoClanListDao geoClanListDao() {
    if (_geoClanListDao != null) {
      return _geoClanListDao;
    } else {
      synchronized(this) {
        if(_geoClanListDao == null) {
          _geoClanListDao = new GeoClanListDao_Impl(this);
        }
        return _geoClanListDao;
      }
    }
  }
}
