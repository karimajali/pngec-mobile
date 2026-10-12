package com.pngelectoral.data.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pngelectoral.ui.model.DistrictList;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class DistrictListDao_Impl implements DistrictListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfDistrictList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfDistrictList;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfDistrictList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public DistrictListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDistrictList = new EntityInsertionAdapter<DistrictList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `DistrictList`(`districtId`,`districtName`,`provinceId`,`regionId`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DistrictList value) {
        if (value.districtId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.districtId);
        }
        if (value.districtName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.districtName);
        }
        if (value.provinceId == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.provinceId);
        }
        if (value.regionId == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.regionId);
        }
      }
    };
    this.__deletionAdapterOfDistrictList = new EntityDeletionOrUpdateAdapter<DistrictList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `DistrictList` WHERE `districtId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DistrictList value) {
        if (value.districtId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.districtId);
        }
      }
    };
    this.__updateAdapterOfDistrictList = new EntityDeletionOrUpdateAdapter<DistrictList>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `DistrictList` SET `districtId` = ?,`districtName` = ?,`provinceId` = ?,`regionId` = ? WHERE `districtId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DistrictList value) {
        if (value.districtId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.districtId);
        }
        if (value.districtName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.districtName);
        }
        if (value.provinceId == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.provinceId);
        }
        if (value.regionId == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.regionId);
        }
        if (value.districtId == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.districtId);
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  FROM districtlist";
        return _query;
      }
    };
  }

  @Override
  public void insert(DistrictList district) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfDistrictList.insert(district);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(List<DistrictList> district) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfDistrictList.insert(district);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(DistrictList district) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfDistrictList.handle(district);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(DistrictList district) {
    __db.beginTransaction();
    try {
      __updateAdapterOfDistrictList.handle(district);
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
  public List<DistrictList> getAll() {
    final String _sql = "SELECT * FROM districtlist";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfDistrictName = _cursor.getColumnIndexOrThrow("districtName");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<DistrictList> _result = new ArrayList<DistrictList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DistrictList _item;
        _item = new DistrictList();
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _item.districtId = null;
        } else {
          _item.districtId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        _item.districtName = _cursor.getString(_cursorIndexOfDistrictName);
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
  public List<DistrictList> getDistrictOfProvision(Integer provinceId) {
    final String _sql = "SELECT * FROM districtlist WHERE provinceId ==? ORDER BY districtId ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (provinceId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, provinceId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfDistrictName = _cursor.getColumnIndexOrThrow("districtName");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<DistrictList> _result = new ArrayList<DistrictList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DistrictList _item;
        _item = new DistrictList();
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _item.districtId = null;
        } else {
          _item.districtId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        _item.districtName = _cursor.getString(_cursorIndexOfDistrictName);
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
  public List<DistrictList> getDistrictOfRegionAndProvision(Integer regionId, Integer provinceId) {
    final String _sql = "SELECT * FROM districtlist WHERE regionId ==? And provinceId ==? ORDER BY districtId ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
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
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfDistrictName = _cursor.getColumnIndexOrThrow("districtName");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<DistrictList> _result = new ArrayList<DistrictList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DistrictList _item;
        _item = new DistrictList();
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _item.districtId = null;
        } else {
          _item.districtId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        _item.districtName = _cursor.getString(_cursorIndexOfDistrictName);
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
  public DistrictList getDPODistrict(Integer districtId) {
    final String _sql = "SELECT * FROM districtlist WHERE districtId ==?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (districtId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, districtId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfDistrictName = _cursor.getColumnIndexOrThrow("districtName");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final DistrictList _result;
      if(_cursor.moveToFirst()) {
        _result = new DistrictList();
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _result.districtId = null;
        } else {
          _result.districtId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        _result.districtName = _cursor.getString(_cursorIndexOfDistrictName);
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
