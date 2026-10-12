package com.pngelectoral.data.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pngelectoral.ui.model.ProvinceList;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class ProvinceListDao_Impl implements ProvinceListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfProvinceList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfProvinceList;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfProvinceList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ProvinceListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProvinceList = new EntityInsertionAdapter<ProvinceList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `ProvinceList`(`provinceId`,`provinceName`,`regionId`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ProvinceList value) {
        if (value.provinceId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.provinceId);
        }
        if (value.provinceName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.provinceName);
        }
        if (value.regionId == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.regionId);
        }
      }
    };
    this.__deletionAdapterOfProvinceList = new EntityDeletionOrUpdateAdapter<ProvinceList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ProvinceList` WHERE `provinceId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ProvinceList value) {
        if (value.provinceId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.provinceId);
        }
      }
    };
    this.__updateAdapterOfProvinceList = new EntityDeletionOrUpdateAdapter<ProvinceList>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ProvinceList` SET `provinceId` = ?,`provinceName` = ?,`regionId` = ? WHERE `provinceId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ProvinceList value) {
        if (value.provinceId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.provinceId);
        }
        if (value.provinceName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.provinceName);
        }
        if (value.regionId == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.regionId);
        }
        if (value.provinceId == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.provinceId);
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  FROM provincelist";
        return _query;
      }
    };
  }

  @Override
  public void insert(ProvinceList province) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfProvinceList.insert(province);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(List<ProvinceList> province) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfProvinceList.insert(province);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(ProvinceList province) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfProvinceList.handle(province);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(ProvinceList province) {
    __db.beginTransaction();
    try {
      __updateAdapterOfProvinceList.handle(province);
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
  public List<ProvinceList> getAll() {
    final String _sql = "SELECT * FROM provincelist";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfProvinceName = _cursor.getColumnIndexOrThrow("provinceName");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<ProvinceList> _result = new ArrayList<ProvinceList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ProvinceList _item;
        _item = new ProvinceList();
        if (_cursor.isNull(_cursorIndexOfProvinceId)) {
          _item.provinceId = null;
        } else {
          _item.provinceId = _cursor.getInt(_cursorIndexOfProvinceId);
        }
        _item.provinceName = _cursor.getString(_cursorIndexOfProvinceName);
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
  public List<ProvinceList> getProvinceOfRegion(Integer regionId) {
    final String _sql = "SELECT * FROM provincelist WHERE regionId ==? ORDER BY provinceId ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (regionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, regionId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfProvinceName = _cursor.getColumnIndexOrThrow("provinceName");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<ProvinceList> _result = new ArrayList<ProvinceList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ProvinceList _item;
        _item = new ProvinceList();
        if (_cursor.isNull(_cursorIndexOfProvinceId)) {
          _item.provinceId = null;
        } else {
          _item.provinceId = _cursor.getInt(_cursorIndexOfProvinceId);
        }
        _item.provinceName = _cursor.getString(_cursorIndexOfProvinceName);
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
  public ProvinceList getDPOProvince(Integer provinceId) {
    final String _sql = "SELECT * FROM provincelist WHERE provinceId ==?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (provinceId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, provinceId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfProvinceName = _cursor.getColumnIndexOrThrow("provinceName");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final ProvinceList _result;
      if(_cursor.moveToFirst()) {
        _result = new ProvinceList();
        if (_cursor.isNull(_cursorIndexOfProvinceId)) {
          _result.provinceId = null;
        } else {
          _result.provinceId = _cursor.getInt(_cursorIndexOfProvinceId);
        }
        _result.provinceName = _cursor.getString(_cursorIndexOfProvinceName);
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
