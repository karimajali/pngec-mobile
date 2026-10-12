package com.pngelectoral.data.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pngelectoral.ui.model.LLGList;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class LLGListDao_Impl implements LLGListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfLLGList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfLLGList;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfLLGList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public LLGListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLLGList = new EntityInsertionAdapter<LLGList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `LLGList`(`llgId`,`llgName`,`districtId`,`provinceId`,`regionId`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LLGList value) {
        if (value.llgId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.llgId);
        }
        if (value.llgName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.llgName);
        }
        if (value.districtId == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.districtId);
        }
        if (value.provinceId == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.provinceId);
        }
        if (value.regionId == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.regionId);
        }
      }
    };
    this.__deletionAdapterOfLLGList = new EntityDeletionOrUpdateAdapter<LLGList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `LLGList` WHERE `llgId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LLGList value) {
        if (value.llgId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.llgId);
        }
      }
    };
    this.__updateAdapterOfLLGList = new EntityDeletionOrUpdateAdapter<LLGList>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `LLGList` SET `llgId` = ?,`llgName` = ?,`districtId` = ?,`provinceId` = ?,`regionId` = ? WHERE `llgId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LLGList value) {
        if (value.llgId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.llgId);
        }
        if (value.llgName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.llgName);
        }
        if (value.districtId == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.districtId);
        }
        if (value.provinceId == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.provinceId);
        }
        if (value.regionId == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.regionId);
        }
        if (value.llgId == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.llgId);
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  FROM llglist";
        return _query;
      }
    };
  }

  @Override
  public void insert(LLGList llg) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfLLGList.insert(llg);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(List<LLGList> llg) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfLLGList.insert(llg);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(LLGList llg) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfLLGList.handle(llg);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(LLGList llg) {
    __db.beginTransaction();
    try {
      __updateAdapterOfLLGList.handle(llg);
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
  public List<LLGList> getAll() {
    final String _sql = "SELECT * FROM llglist";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfLlgName = _cursor.getColumnIndexOrThrow("llgName");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<LLGList> _result = new ArrayList<LLGList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LLGList _item;
        _item = new LLGList();
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _item.llgId = null;
        } else {
          _item.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        _item.llgName = _cursor.getString(_cursorIndexOfLlgName);
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
  public List<LLGList> getLLGOfProvision(Integer provinceId) {
    final String _sql = "SELECT * FROM llglist WHERE provinceId ==? ORDER BY llgId ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (provinceId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, provinceId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfLlgName = _cursor.getColumnIndexOrThrow("llgName");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<LLGList> _result = new ArrayList<LLGList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LLGList _item;
        _item = new LLGList();
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _item.llgId = null;
        } else {
          _item.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        _item.llgName = _cursor.getString(_cursorIndexOfLlgName);
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
  public List<LLGList> getLLGOfProvisionAndDistrict(Integer provinceId, Integer districtId) {
    final String _sql = "SELECT * FROM llglist WHERE provinceId ==? And districtId==? ORDER BY llgId ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
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
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfLlgName = _cursor.getColumnIndexOrThrow("llgName");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<LLGList> _result = new ArrayList<LLGList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LLGList _item;
        _item = new LLGList();
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _item.llgId = null;
        } else {
          _item.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        _item.llgName = _cursor.getString(_cursorIndexOfLlgName);
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
  public List<LLGList> getLLGOfDistrict(Integer districtId) {
    final String _sql = "SELECT * FROM llglist WHERE  districtId==? ORDER BY llgId ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (districtId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, districtId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfLlgName = _cursor.getColumnIndexOrThrow("llgName");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<LLGList> _result = new ArrayList<LLGList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LLGList _item;
        _item = new LLGList();
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _item.llgId = null;
        } else {
          _item.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        _item.llgName = _cursor.getString(_cursorIndexOfLlgName);
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
  public List<LLGList> getLLGOfRegionAndProvisionAndDistrict(Integer regionId, Integer provinceId,
      Integer districtId) {
    final String _sql = "SELECT * FROM llglist WHERE regionId ==? And provinceId ==? And districtId==? ORDER BY llgId ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
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
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfLlgName = _cursor.getColumnIndexOrThrow("llgName");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final List<LLGList> _result = new ArrayList<LLGList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LLGList _item;
        _item = new LLGList();
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _item.llgId = null;
        } else {
          _item.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        _item.llgName = _cursor.getString(_cursorIndexOfLlgName);
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
  public LLGList getDPOLLG(Integer llgId) {
    final String _sql = "SELECT * FROM llglist WHERE llgId ==?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (llgId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, llgId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLlgId = _cursor.getColumnIndexOrThrow("llgId");
      final int _cursorIndexOfLlgName = _cursor.getColumnIndexOrThrow("llgName");
      final int _cursorIndexOfDistrictId = _cursor.getColumnIndexOrThrow("districtId");
      final int _cursorIndexOfProvinceId = _cursor.getColumnIndexOrThrow("provinceId");
      final int _cursorIndexOfRegionId = _cursor.getColumnIndexOrThrow("regionId");
      final LLGList _result;
      if(_cursor.moveToFirst()) {
        _result = new LLGList();
        if (_cursor.isNull(_cursorIndexOfLlgId)) {
          _result.llgId = null;
        } else {
          _result.llgId = _cursor.getInt(_cursorIndexOfLlgId);
        }
        _result.llgName = _cursor.getString(_cursorIndexOfLlgName);
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
