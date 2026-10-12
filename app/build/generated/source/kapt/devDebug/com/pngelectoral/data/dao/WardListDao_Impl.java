package com.pngelectoral.data.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pngelectoral.ui.model.WardList;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class WardListDao_Impl implements WardListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfWardList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfWardList;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfWardList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public WardListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWardList = new EntityInsertionAdapter<WardList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `WardList`(`wardId`,`wardName`,`wardNumber`,`llgId`,`districtId`,`provinceId`,`regionId`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WardList value) {
        if (value.wardId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.wardId);
        }
        if (value.wardName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.wardName);
        }
        if (value.wardNumber == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.wardNumber);
        }
        if (value.llgId == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.llgId);
        }
        if (value.districtId == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.districtId);
        }
        if (value.provinceId == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.provinceId);
        }
        if (value.regionId == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.regionId);
        }
      }
    };
    this.__deletionAdapterOfWardList = new EntityDeletionOrUpdateAdapter<WardList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `WardList` WHERE `wardId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WardList value) {
        if (value.wardId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.wardId);
        }
      }
    };
    this.__updateAdapterOfWardList = new EntityDeletionOrUpdateAdapter<WardList>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `WardList` SET `wardId` = ?,`wardName` = ?,`wardNumber` = ?,`llgId` = ?,`districtId` = ?,`provinceId` = ?,`regionId` = ? WHERE `wardId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WardList value) {
        if (value.wardId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.wardId);
        }
        if (value.wardName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.wardName);
        }
        if (value.wardNumber == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.wardNumber);
        }
        if (value.llgId == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.llgId);
        }
        if (value.districtId == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.districtId);
        }
        if (value.provinceId == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.provinceId);
        }
        if (value.regionId == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.regionId);
        }
        if (value.wardId == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, value.wardId);
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  FROM wardlist";
        return _query;
      }
    };
  }

  @Override
  public void insert(WardList ward) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfWardList.insert(ward);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(List<WardList> ward) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfWardList.insert(ward);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(WardList ward) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfWardList.handle(ward);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(WardList ward) {
    __db.beginTransaction();
    try {
      __updateAdapterOfWardList.handle(ward);
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
  public List<WardList> getAll() {
    final String _sql = "SELECT * FROM wardlist ORDER BY wardNumber ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final int _cursorIndexOfWardName = _cursor.getColumnIndexOrThrow("wardName");
      final int _cursorIndexOfWardNumber = _cursor.getColumnIndexOrThrow("wardNumber");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<WardList> _result = new ArrayList<WardList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final WardList _item;
        _item = new WardList();
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _item.wardId = null;
        } else {
          _item.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _item.wardName = _cursor.getString(_cursorIndexOfWardName);
        if (_cursor.isNull(_cursorIndexOfWardNumber)) {
          _item.wardNumber = null;
        } else {
          _item.wardNumber = _cursor.getInt(_cursorIndexOfWardNumber);
        }
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
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<WardList> getWardListOfProvisionAndLLG(Integer provinceId, Integer llgId) {
    final String _sql = "SELECT * FROM wardlist WHERE provinceId ==? And llgId ==? ORDER BY wardNumber ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (provinceId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, provinceId);
    }
    _argIndex = 2;
    if (llgId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, llgId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final int _cursorIndexOfWardName = _cursor.getColumnIndexOrThrow("wardName");
      final int _cursorIndexOfWardNumber = _cursor.getColumnIndexOrThrow("wardNumber");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<WardList> _result = new ArrayList<WardList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final WardList _item;
        _item = new WardList();
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _item.wardId = null;
        } else {
          _item.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _item.wardName = _cursor.getString(_cursorIndexOfWardName);
        if (_cursor.isNull(_cursorIndexOfWardNumber)) {
          _item.wardNumber = null;
        } else {
          _item.wardNumber = _cursor.getInt(_cursorIndexOfWardNumber);
        }
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
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<WardList> getWardListOfProvisionAndDistrictAndLLG(Integer provinceId,
      Integer districtId, Integer llgId) {
    final String _sql = "SELECT * FROM wardlist WHERE provinceId ==? And districtId ==? And llgId ==? ORDER BY wardNumber ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
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
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final int _cursorIndexOfWardName = _cursor.getColumnIndexOrThrow("wardName");
      final int _cursorIndexOfWardNumber = _cursor.getColumnIndexOrThrow("wardNumber");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<WardList> _result = new ArrayList<WardList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final WardList _item;
        _item = new WardList();
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _item.wardId = null;
        } else {
          _item.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _item.wardName = _cursor.getString(_cursorIndexOfWardName);
        if (_cursor.isNull(_cursorIndexOfWardNumber)) {
          _item.wardNumber = null;
        } else {
          _item.wardNumber = _cursor.getInt(_cursorIndexOfWardNumber);
        }
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
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<WardList> getWardListOfDistrictAndLLG(Integer districtId, Integer llgId) {
    final String _sql = "SELECT * FROM wardlist WHERE districtId ==? And llgId ==? ORDER BY wardNumber ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
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
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final int _cursorIndexOfWardName = _cursor.getColumnIndexOrThrow("wardName");
      final int _cursorIndexOfWardNumber = _cursor.getColumnIndexOrThrow("wardNumber");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<WardList> _result = new ArrayList<WardList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final WardList _item;
        _item = new WardList();
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _item.wardId = null;
        } else {
          _item.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _item.wardName = _cursor.getString(_cursorIndexOfWardName);
        if (_cursor.isNull(_cursorIndexOfWardNumber)) {
          _item.wardNumber = null;
        } else {
          _item.wardNumber = _cursor.getInt(_cursorIndexOfWardNumber);
        }
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
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<WardList> getWardListOfRegionAndProvinceAndDistrictAndLLG(Integer regionId,
      Integer provinceId, Integer districtId, Integer llgId) {
    final String _sql = "SELECT * FROM wardlist WHERE regionId ==? And provinceId ==? And districtId ==? And llgId ==? ORDER BY wardNumber ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
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
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final int _cursorIndexOfWardName = _cursor.getColumnIndexOrThrow("wardName");
      final int _cursorIndexOfWardNumber = _cursor.getColumnIndexOrThrow("wardNumber");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<WardList> _result = new ArrayList<WardList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final WardList _item;
        _item = new WardList();
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _item.wardId = null;
        } else {
          _item.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _item.wardName = _cursor.getString(_cursorIndexOfWardName);
        if (_cursor.isNull(_cursorIndexOfWardNumber)) {
          _item.wardNumber = null;
        } else {
          _item.wardNumber = _cursor.getInt(_cursorIndexOfWardNumber);
        }
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
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public WardList getDPOWard(Integer wardId) {
    final String _sql = "SELECT * FROM wardlist WHERE wardId ==?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (wardId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, wardId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final int _cursorIndexOfWardName = _cursor.getColumnIndexOrThrow("wardName");
      final int _cursorIndexOfWardNumber = _cursor.getColumnIndexOrThrow("wardNumber");
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final WardList _result;
      if(_cursor.moveToFirst()) {
        _result = new WardList();
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _result.wardId = null;
        } else {
          _result.wardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _result.wardName = _cursor.getString(_cursorIndexOfWardName);
        if (_cursor.isNull(_cursorIndexOfWardNumber)) {
          _result.wardNumber = null;
        } else {
          _result.wardNumber = _cursor.getInt(_cursorIndexOfWardNumber);
        }
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
