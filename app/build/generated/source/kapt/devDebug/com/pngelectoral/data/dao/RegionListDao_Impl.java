package com.pngelectoral.data.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pngelectoral.ui.model.RegionList;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class RegionListDao_Impl implements RegionListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfRegionList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfRegionList;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfRegionList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public RegionListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRegionList = new EntityInsertionAdapter<RegionList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `RegionList`(`regionId`,`regionName`,`selected`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RegionList value) {
        if (value.regionId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.regionId);
        }
        if (value.regionName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.regionName);
        }
        final Integer _tmp;
        _tmp = value.selected == null ? null : (value.selected ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
      }
    };
    this.__deletionAdapterOfRegionList = new EntityDeletionOrUpdateAdapter<RegionList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `RegionList` WHERE `regionId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RegionList value) {
        if (value.regionId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.regionId);
        }
      }
    };
    this.__updateAdapterOfRegionList = new EntityDeletionOrUpdateAdapter<RegionList>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `RegionList` SET `regionId` = ?,`regionName` = ?,`selected` = ? WHERE `regionId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RegionList value) {
        if (value.regionId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.regionId);
        }
        if (value.regionName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.regionName);
        }
        final Integer _tmp;
        _tmp = value.selected == null ? null : (value.selected ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        if (value.regionId == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.regionId);
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  FROM regionlist";
        return _query;
      }
    };
  }

  @Override
  public void insert(RegionList region) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRegionList.insert(region);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(List<RegionList> region) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRegionList.insert(region);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(RegionList region) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfRegionList.handle(region);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(RegionList region) {
    __db.beginTransaction();
    try {
      __updateAdapterOfRegionList.handle(region);
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
  public List<RegionList> getAll() {
    final String _sql = "SELECT * FROM regionlist";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final int _cursorIndexOfRegionName = _cursor.getColumnIndexOrThrow("regionName");
      final int _cursorIndexOfSelected = _cursor.getColumnIndexOrThrow("selected");
      final List<RegionList> _result = new ArrayList<RegionList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final RegionList _item;
        _item = new RegionList();
        if (_cursor.isNull(_cursorIndexOfRegionId)) {
          _item.regionId = null;
        } else {
          _item.regionId = _cursor.getInt(_cursorIndexOfRegionId);
        }
        _item.regionName = _cursor.getString(_cursorIndexOfRegionName);
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfSelected)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfSelected);
        }
        _item.selected = _tmp == null ? null : _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public RegionList getDPORegion(Integer regionId) {
    final String _sql = "SELECT * FROM regionlist WHERE regionId ==?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (regionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, regionId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final int _cursorIndexOfRegionName = _cursor.getColumnIndexOrThrow("regionName");
      final int _cursorIndexOfSelected = _cursor.getColumnIndexOrThrow("selected");
      final RegionList _result;
      if(_cursor.moveToFirst()) {
        _result = new RegionList();
        if (_cursor.isNull(_cursorIndexOfRegionId)) {
          _result.regionId = null;
        } else {
          _result.regionId = _cursor.getInt(_cursorIndexOfRegionId);
        }
        _result.regionName = _cursor.getString(_cursorIndexOfRegionName);
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfSelected)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfSelected);
        }
        _result.selected = _tmp == null ? null : _tmp != 0;
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
