package com.pngelectoral.data.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pngelectoral.ui.model.MKAClanListData;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class ClanListDao_Impl implements ClanListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMKAClanListData;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfMKAClanListData;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfMKAClanListData;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ClanListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMKAClanListData = new EntityInsertionAdapter<MKAClanListData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `mkaclanlist`(`clanId`,`clanName`,`description`,`responseCode`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MKAClanListData value) {
        if (value.getClanId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getClanId());
        }
        if (value.getClanName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getClanName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getResponseCode() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getResponseCode());
        }
      }
    };
    this.__deletionAdapterOfMKAClanListData = new EntityDeletionOrUpdateAdapter<MKAClanListData>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `mkaclanlist` WHERE `clanId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MKAClanListData value) {
        if (value.getClanId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getClanId());
        }
      }
    };
    this.__updateAdapterOfMKAClanListData = new EntityDeletionOrUpdateAdapter<MKAClanListData>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `mkaclanlist` SET `clanId` = ?,`clanName` = ?,`description` = ?,`responseCode` = ? WHERE `clanId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MKAClanListData value) {
        if (value.getClanId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getClanId());
        }
        if (value.getClanName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getClanName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getResponseCode() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getResponseCode());
        }
        if (value.getClanId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getClanId());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM mkaclanlist";
        return _query;
      }
    };
  }

  @Override
  public void insert(MKAClanListData clanModel) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMKAClanListData.insert(clanModel);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(List<MKAClanListData> clanList) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMKAClanListData.insert(clanList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(MKAClanListData clanModel) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfMKAClanListData.handle(clanModel);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(MKAClanListData clanModel) {
    __db.beginTransaction();
    try {
      __updateAdapterOfMKAClanListData.handle(clanModel);
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
  public List<MKAClanListData> getAll() {
    final String _sql = "SELECT * FROM mkaclanlist";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfClanId = _cursor.getColumnIndexOrThrow("clanId");
      final int _cursorIndexOfClanName = _cursor.getColumnIndexOrThrow("clanName");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfResponseCode = _cursor.getColumnIndexOrThrow("responseCode");
      final List<MKAClanListData> _result = new ArrayList<MKAClanListData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MKAClanListData _item;
        _item = new MKAClanListData();
        final Integer _tmpClanId;
        if (_cursor.isNull(_cursorIndexOfClanId)) {
          _tmpClanId = null;
        } else {
          _tmpClanId = _cursor.getInt(_cursorIndexOfClanId);
        }
        _item.setClanId(_tmpClanId);
        final String _tmpClanName;
        _tmpClanName = _cursor.getString(_cursorIndexOfClanName);
        _item.setClanName(_tmpClanName);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final Integer _tmpResponseCode;
        if (_cursor.isNull(_cursorIndexOfResponseCode)) {
          _tmpResponseCode = null;
        } else {
          _tmpResponseCode = _cursor.getInt(_cursorIndexOfResponseCode);
        }
        _item.setResponseCode(_tmpResponseCode);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public MKAClanListData getDataForClan(Integer id) {
    final String _sql = "SELECT * FROM mkaclanlist WHERE clanId == ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfClanId = _cursor.getColumnIndexOrThrow("clanId");
      final int _cursorIndexOfClanName = _cursor.getColumnIndexOrThrow("clanName");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfResponseCode = _cursor.getColumnIndexOrThrow("responseCode");
      final MKAClanListData _result;
      if(_cursor.moveToFirst()) {
        _result = new MKAClanListData();
        final Integer _tmpClanId;
        if (_cursor.isNull(_cursorIndexOfClanId)) {
          _tmpClanId = null;
        } else {
          _tmpClanId = _cursor.getInt(_cursorIndexOfClanId);
        }
        _result.setClanId(_tmpClanId);
        final String _tmpClanName;
        _tmpClanName = _cursor.getString(_cursorIndexOfClanName);
        _result.setClanName(_tmpClanName);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _result.setDescription(_tmpDescription);
        final Integer _tmpResponseCode;
        if (_cursor.isNull(_cursorIndexOfResponseCode)) {
          _tmpResponseCode = null;
        } else {
          _tmpResponseCode = _cursor.getInt(_cursorIndexOfResponseCode);
        }
        _result.setResponseCode(_tmpResponseCode);
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
