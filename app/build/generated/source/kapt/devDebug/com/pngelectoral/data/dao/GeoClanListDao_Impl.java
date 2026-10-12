package com.pngelectoral.data.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pngelectoral.ui.model.ClanListData;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class GeoClanListDao_Impl implements GeoClanListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfClanListData;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfClanListData;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfClanListData;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public GeoClanListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfClanListData = new EntityInsertionAdapter<ClanListData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `clanlist`(`clanId`,`wardId`,`clanName`,`description`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ClanListData value) {
        stmt.bindLong(1, value.getClanId());
        if (value.getWardId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getWardId());
        }
        if (value.getClanName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getClanName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
      }
    };
    this.__deletionAdapterOfClanListData = new EntityDeletionOrUpdateAdapter<ClanListData>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `clanlist` WHERE `clanId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ClanListData value) {
        stmt.bindLong(1, value.getClanId());
      }
    };
    this.__updateAdapterOfClanListData = new EntityDeletionOrUpdateAdapter<ClanListData>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `clanlist` SET `clanId` = ?,`wardId` = ?,`clanName` = ?,`description` = ? WHERE `clanId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ClanListData value) {
        stmt.bindLong(1, value.getClanId());
        if (value.getWardId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getWardId());
        }
        if (value.getClanName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getClanName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        stmt.bindLong(5, value.getClanId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM clanlist";
        return _query;
      }
    };
  }

  @Override
  public void insert(ClanListData clanModel) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfClanListData.insert(clanModel);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(List<ClanListData> clanList) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfClanListData.insert(clanList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(ClanListData clanModel) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfClanListData.handle(clanModel);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(ClanListData clanModel) {
    __db.beginTransaction();
    try {
      __updateAdapterOfClanListData.handle(clanModel);
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
  public List<ClanListData> getAll() {
    final String _sql = "SELECT * FROM clanlist";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfClanId = _cursor.getColumnIndexOrThrow("clanId");
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final int _cursorIndexOfClanName = _cursor.getColumnIndexOrThrow("clanName");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final List<ClanListData> _result = new ArrayList<ClanListData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ClanListData _item;
        _item = new ClanListData();
        final int _tmpClanId;
        _tmpClanId = _cursor.getInt(_cursorIndexOfClanId);
        _item.setClanId(_tmpClanId);
        final Integer _tmpWardId;
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _tmpWardId = null;
        } else {
          _tmpWardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _item.setWardId(_tmpWardId);
        final String _tmpClanName;
        _tmpClanName = _cursor.getString(_cursorIndexOfClanName);
        _item.setClanName(_tmpClanName);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public ClanListData getDataForClan(Integer id) {
    final String _sql = "SELECT * FROM clanlist WHERE clanId == ? ORDER BY LOWER(clanName) DESC";
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
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final int _cursorIndexOfClanName = _cursor.getColumnIndexOrThrow("clanName");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final ClanListData _result;
      if(_cursor.moveToFirst()) {
        _result = new ClanListData();
        final int _tmpClanId;
        _tmpClanId = _cursor.getInt(_cursorIndexOfClanId);
        _result.setClanId(_tmpClanId);
        final Integer _tmpWardId;
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _tmpWardId = null;
        } else {
          _tmpWardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _result.setWardId(_tmpWardId);
        final String _tmpClanName;
        _tmpClanName = _cursor.getString(_cursorIndexOfClanName);
        _result.setClanName(_tmpClanName);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _result.setDescription(_tmpDescription);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ClanListData> getDataForClanByWard(Integer id) {
    final String _sql = "SELECT * FROM clanlist WHERE wardId == ? ORDER BY LOWER(clanName) DESC";
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
      final int _cursorIndexOfWardId = _cursor.getColumnIndexOrThrow("wardId");
      final int _cursorIndexOfClanName = _cursor.getColumnIndexOrThrow("clanName");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final List<ClanListData> _result = new ArrayList<ClanListData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ClanListData _item;
        _item = new ClanListData();
        final int _tmpClanId;
        _tmpClanId = _cursor.getInt(_cursorIndexOfClanId);
        _item.setClanId(_tmpClanId);
        final Integer _tmpWardId;
        if (_cursor.isNull(_cursorIndexOfWardId)) {
          _tmpWardId = null;
        } else {
          _tmpWardId = _cursor.getInt(_cursorIndexOfWardId);
        }
        _item.setWardId(_tmpWardId);
        final String _tmpClanName;
        _tmpClanName = _cursor.getString(_cursorIndexOfClanName);
        _item.setClanName(_tmpClanName);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
