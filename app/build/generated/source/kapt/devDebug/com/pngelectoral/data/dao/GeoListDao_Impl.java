package com.pngelectoral.data.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pngelectoral.ui.model.MKAGeoListData;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class GeoListDao_Impl implements GeoListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMKAGeoListData;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfMKAGeoListData;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfMKAGeoListData;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public GeoListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMKAGeoListData = new EntityInsertionAdapter<MKAGeoListData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `mkageolist`(`actionBy`,`districtId`,`districtName`,`llGs`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MKAGeoListData value) {
        if (value.getActionBy() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getActionBy());
        }
        if (value.getDistrictId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getDistrictId());
        }
        if (value.getDistrictName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDistrictName());
        }
        if (value.getLlGs() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getLlGs());
        }
      }
    };
    this.__deletionAdapterOfMKAGeoListData = new EntityDeletionOrUpdateAdapter<MKAGeoListData>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `mkageolist` WHERE `districtId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MKAGeoListData value) {
        if (value.getDistrictId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getDistrictId());
        }
      }
    };
    this.__updateAdapterOfMKAGeoListData = new EntityDeletionOrUpdateAdapter<MKAGeoListData>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `mkageolist` SET `actionBy` = ?,`districtId` = ?,`districtName` = ?,`llGs` = ? WHERE `districtId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MKAGeoListData value) {
        if (value.getActionBy() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getActionBy());
        }
        if (value.getDistrictId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getDistrictId());
        }
        if (value.getDistrictName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDistrictName());
        }
        if (value.getLlGs() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getLlGs());
        }
        if (value.getDistrictId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getDistrictId());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM mkageolist";
        return _query;
      }
    };
  }

  @Override
  public void insert(MKAGeoListData districtModel) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMKAGeoListData.insert(districtModel);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(List<MKAGeoListData> districtList) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMKAGeoListData.insert(districtList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(MKAGeoListData location) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfMKAGeoListData.handle(location);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(MKAGeoListData location) {
    __db.beginTransaction();
    try {
      __updateAdapterOfMKAGeoListData.handle(location);
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
  public List<MKAGeoListData> getAll() {
    final String _sql = "SELECT * FROM mkageolist";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfActionBy = _cursor.getColumnIndexOrThrow("actionBy");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfDistrictName = _cursor.getColumnIndexOrThrow("districtName");
      final int _cursorIndexOfLlGs = _cursor.getColumnIndexOrThrow("llGs");
      final List<MKAGeoListData> _result = new ArrayList<MKAGeoListData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MKAGeoListData _item;
        _item = new MKAGeoListData();
        final Integer _tmpActionBy;
        if (_cursor.isNull(_cursorIndexOfActionBy)) {
          _tmpActionBy = null;
        } else {
          _tmpActionBy = _cursor.getInt(_cursorIndexOfActionBy);
        }
        _item.setActionBy(_tmpActionBy);
        final Integer _tmpDistrictId;
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _tmpDistrictId = null;
        } else {
          _tmpDistrictId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        _item.setDistrictId(_tmpDistrictId);
        final String _tmpDistrictName;
        _tmpDistrictName = _cursor.getString(_cursorIndexOfDistrictName);
        _item.setDistrictName(_tmpDistrictName);
        final String _tmpLlGs;
        _tmpLlGs = _cursor.getString(_cursorIndexOfLlGs);
        _item.setLlGs(_tmpLlGs);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public MKAGeoListData getDataForDistrict(Integer id) {
    final String _sql = "SELECT * FROM mkageolist WHERE districtId == ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfActionBy = _cursor.getColumnIndexOrThrow("actionBy");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfDistrictName = _cursor.getColumnIndexOrThrow("districtName");
      final int _cursorIndexOfLlGs = _cursor.getColumnIndexOrThrow("llGs");
      final MKAGeoListData _result;
      if(_cursor.moveToFirst()) {
        _result = new MKAGeoListData();
        final Integer _tmpActionBy;
        if (_cursor.isNull(_cursorIndexOfActionBy)) {
          _tmpActionBy = null;
        } else {
          _tmpActionBy = _cursor.getInt(_cursorIndexOfActionBy);
        }
        _result.setActionBy(_tmpActionBy);
        final Integer _tmpDistrictId;
        if (_cursor.isNull(_cursorIndexOfDistrictId)) {
          _tmpDistrictId = null;
        } else {
          _tmpDistrictId = _cursor.getInt(_cursorIndexOfDistrictId);
        }
        _result.setDistrictId(_tmpDistrictId);
        final String _tmpDistrictName;
        _tmpDistrictName = _cursor.getString(_cursorIndexOfDistrictName);
        _result.setDistrictName(_tmpDistrictName);
        final String _tmpLlGs;
        _tmpLlGs = _cursor.getString(_cursorIndexOfLlGs);
        _result.setLlGs(_tmpLlGs);
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
