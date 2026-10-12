package com.pngelectoral.data.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pngelectoral.ui.model.OccupationList;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class OccupationListDao_Impl implements OccupationListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfOccupationList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfOccupationList;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfOccupationList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public OccupationListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOccupationList = new EntityInsertionAdapter<OccupationList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `OccupationList`(`occupationPK`,`occupationName`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, OccupationList value) {
        if (value.occupationPK == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.occupationPK);
        }
        if (value.occupationName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.occupationName);
        }
      }
    };
    this.__deletionAdapterOfOccupationList = new EntityDeletionOrUpdateAdapter<OccupationList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `OccupationList` WHERE `occupationPK` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, OccupationList value) {
        if (value.occupationPK == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.occupationPK);
        }
      }
    };
    this.__updateAdapterOfOccupationList = new EntityDeletionOrUpdateAdapter<OccupationList>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `OccupationList` SET `occupationPK` = ?,`occupationName` = ? WHERE `occupationPK` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, OccupationList value) {
        if (value.occupationPK == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.occupationPK);
        }
        if (value.occupationName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.occupationName);
        }
        if (value.occupationPK == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.occupationPK);
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  FROM occupationlist";
        return _query;
      }
    };
  }

  @Override
  public void insert(OccupationList occupation) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfOccupationList.insert(occupation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(List<OccupationList> occupation) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfOccupationList.insert(occupation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(OccupationList occupation) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfOccupationList.handle(occupation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(OccupationList occupation) {
    __db.beginTransaction();
    try {
      __updateAdapterOfOccupationList.handle(occupation);
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
  public List<OccupationList> getAll() {
    final String _sql = "SELECT * FROM occupationlist ORDER BY occupationName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfOccupationPK = _cursor.getColumnIndexOrThrow("occupationPK");
      final int _cursorIndexOfOccupationName = _cursor.getColumnIndexOrThrow("occupationName");
      final List<OccupationList> _result = new ArrayList<OccupationList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final OccupationList _item;
        _item = new OccupationList();
        if (_cursor.isNull(_cursorIndexOfOccupationPK)) {
          _item.occupationPK = null;
        } else {
          _item.occupationPK = _cursor.getInt(_cursorIndexOfOccupationPK);
        }
        _item.occupationName = _cursor.getString(_cursorIndexOfOccupationName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public OccupationList getOccupation(Integer occupationID) {
    final String _sql = "SELECT * FROM occupationlist WHERE occupationPK ==?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (occupationID == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, occupationID);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfOccupationPK = _cursor.getColumnIndexOrThrow("occupationPK");
      final int _cursorIndexOfOccupationName = _cursor.getColumnIndexOrThrow("occupationName");
      final OccupationList _result;
      if(_cursor.moveToFirst()) {
        _result = new OccupationList();
        if (_cursor.isNull(_cursorIndexOfOccupationPK)) {
          _result.occupationPK = null;
        } else {
          _result.occupationPK = _cursor.getInt(_cursorIndexOfOccupationPK);
        }
        _result.occupationName = _cursor.getString(_cursorIndexOfOccupationName);
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
