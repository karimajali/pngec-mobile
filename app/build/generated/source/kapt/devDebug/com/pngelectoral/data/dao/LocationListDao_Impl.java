package com.pngelectoral.data.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pngelectoral.ui.model.LocationList;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class LocationListDao_Impl implements LocationListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfLocationList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfLocationList;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfLocationList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public LocationListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLocationList = new EntityInsertionAdapter<LocationList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `LocationList`(`locationId`,`locationName`,`llgId`,`districtId`,`provinceId`,`regionId`,`wardId`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocationList value) {
        if (value.locationId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.locationId);
        }
        if (value.locationName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.locationName);
        }
        if (value.llgId == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.llgId);
        }
        if (value.districtId == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.districtId);
        }
        if (value.provinceId == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.provinceId);
        }
        if (value.regionId == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.regionId);
        }
        if (value.wardId == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.wardId);
        }
      }
    };
    this.__deletionAdapterOfLocationList = new EntityDeletionOrUpdateAdapter<LocationList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `LocationList` WHERE `locationId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocationList value) {
        if (value.locationId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.locationId);
        }
      }
    };
    this.__updateAdapterOfLocationList = new EntityDeletionOrUpdateAdapter<LocationList>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `LocationList` SET `locationId` = ?,`locationName` = ?,`llgId` = ?,`districtId` = ?,`provinceId` = ?,`regionId` = ?,`wardId` = ? WHERE `locationId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocationList value) {
        if (value.locationId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.locationId);
        }
        if (value.locationName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.locationName);
        }
        if (value.llgId == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.llgId);
        }
        if (value.districtId == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.districtId);
        }
        if (value.provinceId == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.provinceId);
        }
        if (value.regionId == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.regionId);
        }
        if (value.wardId == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.wardId);
        }
        if (value.locationId == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, value.locationId);
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  FROM locationlist";
        return _query;
      }
    };
  }

  @Override
  public void insert(LocationList location) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfLocationList.insert(location);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(List<LocationList> location) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfLocationList.insert(location);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(LocationList location) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfLocationList.handle(location);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(LocationList location) {
    __db.beginTransaction();
    try {
      __updateAdapterOfLocationList.handle(location);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<LocationList> getAll() {
    final String _sql = "SELECT * FROM locationlist";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLocationId = _cursor.getColumnIndexOrThrow("locationId");
      final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("locationName");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final List<LocationList> _result = new ArrayList<LocationList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LocationList _item;
        _item = new LocationList();
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _item.locationId = null;
        } else {
          _item.locationId = _cursor.getInt(_cursorIndexOfLocationId);
        }
        _item.locationName = _cursor.getString(_cursorIndexOfLocationName);
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _item.llgId = null;
        } else {
          _item.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _item.districtId = null;
        } else {
          _item.districtId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        if (_cursor.isNull(_cursorIndexOfProvinceId)) {
          _item.provinceId = null;
        } else {
          _item.provinceId = _cursor.getInt(_cursorIndexOfProvinceId);
        }
        if (_cursor.isNull(_cursorIndexOfRegionId)) {
          _item.regionId = null;
        } else {
          _item.regionId = _cursor.getInt(_cursorIndexOfRegionId);
        }
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _item.wardId = null;
        } else {
          _item.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<LocationList> getLocationListOfRegionAndProvinceAndDistrictAndLLGAndWard(
      Integer regionId, Integer provinceId, Integer districtId, Integer llgId, Integer wardId) {
    final String _sql = "SELECT * FROM locationlist WHERE regionId ==? And provinceId ==? And districtId ==? And llgId ==? And wardId ==? ORDER BY locationName DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 5);
    int _argIndex = 1;
    if (regionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, regionId);
    }
    _argIndex = 2;
    if (provinceId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, provinceId);
    }
    _argIndex = 3;
    if (districtId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, districtId);
    }
    _argIndex = 4;
    if (llgId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, llgId);
    }
    _argIndex = 5;
    if (wardId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, wardId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLocationId = _cursor.getColumnIndexOrThrow("locationId");
      final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("locationName");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final List<LocationList> _result = new ArrayList<LocationList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LocationList _item;
        _item = new LocationList();
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _item.locationId = null;
        } else {
          _item.locationId = _cursor.getInt(_cursorIndexOfLocationId);
        }
        _item.locationName = _cursor.getString(_cursorIndexOfLocationName);
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _item.llgId = null;
        } else {
          _item.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _item.districtId = null;
        } else {
          _item.districtId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        if (_cursor.isNull(_cursorIndexOfProvinceId)) {
          _item.provinceId = null;
        } else {
          _item.provinceId = _cursor.getInt(_cursorIndexOfProvinceId);
        }
        if (_cursor.isNull(_cursorIndexOfRegionId)) {
          _item.regionId = null;
        } else {
          _item.regionId = _cursor.getInt(_cursorIndexOfRegionId);
        }
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _item.wardId = null;
        } else {
          _item.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<LocationList> getLocationListOfWard(Integer wardId) {
    final String _sql = "SELECT * FROM locationlist WHERE wardId ==? ORDER BY locationName DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (wardId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, wardId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLocationId = _cursor.getColumnIndexOrThrow("locationId");
      final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("locationName");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final List<LocationList> _result = new ArrayList<LocationList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LocationList _item;
        _item = new LocationList();
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _item.locationId = null;
        } else {
          _item.locationId = _cursor.getInt(_cursorIndexOfLocationId);
        }
        _item.locationName = _cursor.getString(_cursorIndexOfLocationName);
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _item.llgId = null;
        } else {
          _item.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _item.districtId = null;
        } else {
          _item.districtId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        if (_cursor.isNull(_cursorIndexOfProvinceId)) {
          _item.provinceId = null;
        } else {
          _item.provinceId = _cursor.getInt(_cursorIndexOfProvinceId);
        }
        if (_cursor.isNull(_cursorIndexOfRegionId)) {
          _item.regionId = null;
        } else {
          _item.regionId = _cursor.getInt(_cursorIndexOfRegionId);
        }
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _item.wardId = null;
        } else {
          _item.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<LocationList> getLocationListOfProvinceAndDistrictAndLLGAndWard(Integer provinceId,
      Integer districtId, Integer llgId, Integer wardId) {
    final String _sql = "SELECT * FROM locationlist WHERE provinceId ==? And districtId ==? And llgId ==? And wardId ==? ORDER BY locationName DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    if (provinceId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, provinceId);
    }
    _argIndex = 2;
    if (districtId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, districtId);
    }
    _argIndex = 3;
    if (llgId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, llgId);
    }
    _argIndex = 4;
    if (wardId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, wardId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLocationId = _cursor.getColumnIndexOrThrow("locationId");
      final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("locationName");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final List<LocationList> _result = new ArrayList<LocationList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LocationList _item;
        _item = new LocationList();
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _item.locationId = null;
        } else {
          _item.locationId = _cursor.getInt(_cursorIndexOfLocationId);
        }
        _item.locationName = _cursor.getString(_cursorIndexOfLocationName);
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _item.llgId = null;
        } else {
          _item.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _item.districtId = null;
        } else {
          _item.districtId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        if (_cursor.isNull(_cursorIndexOfProvinceId)) {
          _item.provinceId = null;
        } else {
          _item.provinceId = _cursor.getInt(_cursorIndexOfProvinceId);
        }
        if (_cursor.isNull(_cursorIndexOfRegionId)) {
          _item.regionId = null;
        } else {
          _item.regionId = _cursor.getInt(_cursorIndexOfRegionId);
        }
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _item.wardId = null;
        } else {
          _item.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<LocationList> getLocationListOfDistrictAndLLGAndWard(Integer districtId,
      Integer llgId, Integer wardId) {
    final String _sql = "SELECT * FROM locationlist WHERE districtId ==? And llgId ==? And wardId ==? ORDER BY locationId ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (districtId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, districtId);
    }
    _argIndex = 2;
    if (llgId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, llgId);
    }
    _argIndex = 3;
    if (wardId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, wardId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLocationId = _cursor.getColumnIndexOrThrow("locationId");
      final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("locationName");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final List<LocationList> _result = new ArrayList<LocationList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LocationList _item;
        _item = new LocationList();
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _item.locationId = null;
        } else {
          _item.locationId = _cursor.getInt(_cursorIndexOfLocationId);
        }
        _item.locationName = _cursor.getString(_cursorIndexOfLocationName);
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _item.llgId = null;
        } else {
          _item.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _item.districtId = null;
        } else {
          _item.districtId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        if (_cursor.isNull(_cursorIndexOfProvinceId)) {
          _item.provinceId = null;
        } else {
          _item.provinceId = _cursor.getInt(_cursorIndexOfProvinceId);
        }
        if (_cursor.isNull(_cursorIndexOfRegionId)) {
          _item.regionId = null;
        } else {
          _item.regionId = _cursor.getInt(_cursorIndexOfRegionId);
        }
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _item.wardId = null;
        } else {
          _item.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LocationList getDPOLocation(Integer locationId) {
    final String _sql = "SELECT * FROM locationlist WHERE locationId ==? ORDER BY LOWER(locationName) DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (locationId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, locationId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLocationId = _cursor.getColumnIndexOrThrow("locationId");
      final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("locationName");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final LocationList _result;
      if(_cursor.moveToFirst()) {
        _result = new LocationList();
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _result.locationId = null;
        } else {
          _result.locationId = _cursor.getInt(_cursorIndexOfLocationId);
        }
        _result.locationName = _cursor.getString(_cursorIndexOfLocationName);
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _result.llgId = null;
        } else {
          _result.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _result.districtId = null;
        } else {
          _result.districtId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        if (_cursor.isNull(_cursorIndexOfProvinceId)) {
          _result.provinceId = null;
        } else {
          _result.provinceId = _cursor.getInt(_cursorIndexOfProvinceId);
        }
        if (_cursor.isNull(_cursorIndexOfRegionId)) {
          _result.regionId = null;
        } else {
          _result.regionId = _cursor.getInt(_cursorIndexOfRegionId);
        }
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _result.wardId = null;
        } else {
          _result.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
