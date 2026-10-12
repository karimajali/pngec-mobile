package com.pngelectoral.data.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pngelectoral.ui.model.DpoList;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class DpoListDao_Impl implements DpoListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfDpoList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfDpoList;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfDpoList;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDPOWorkingRollLLGId;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDPOWorkingRollLLGName;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDPOWorkingRollWardId;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDPOWorkingRollWardName;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDPOWorkingRollLocationId;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDPOWorkingRollLocationName;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public DpoListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDpoList = new EntityInsertionAdapter<DpoList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `DpoList`(`userId`,`firstName`,`lastName`,`emailAddress`,`region`,`regionName`,`province`,`provinceName`,`district`,`districtName`,`llg`,`llgName`,`ward`,`wardName`,`location`,`locationName`,`mkaDistrict`,`mkaDistrictName`,`mkallg`,`mkallgName`,`mkaWard`,`mkaWardName`,`mkaLocation`,`mkaLocationName`,`workingRollLLGId`,`workingRollLLGName`,`workingRollWardId`,`workingRollWardName`,`workingRollLocationId`,`workingRollLocationName`,`workingRollMKALLGId`,`workingRollMKALLGName`,`workingRollMKAWardId`,`workingRollMKAWardName`,`workingRollMKALocationId`,`workingRollMKALocationName`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DpoList value) {
        if (value.userId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.userId);
        }
        if (value.firstName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.firstName);
        }
        if (value.lastName == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.lastName);
        }
        if (value.emailAddress == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.emailAddress);
        }
        if (value.region == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.region);
        }
        if (value.regionName == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.regionName);
        }
        if (value.province == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.province);
        }
        if (value.provinceName == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.provinceName);
        }
        if (value.district == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindLong(9, value.district);
        }
        if (value.districtName == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.districtName);
        }
        if (value.llg == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, value.llg);
        }
        if (value.llgName == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.llgName);
        }
        if (value.ward == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindLong(13, value.ward);
        }
        if (value.wardName == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.wardName);
        }
        if (value.location == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindLong(15, value.location);
        }
        if (value.locationName == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.locationName);
        }
        if (value.mkaDistrict == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindLong(17, value.mkaDistrict);
        }
        if (value.mkaDistrictName == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.mkaDistrictName);
        }
        if (value.mkallg == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindLong(19, value.mkallg);
        }
        if (value.mkallgName == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.mkallgName);
        }
        if (value.mkaWard == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindLong(21, value.mkaWard);
        }
        if (value.mkaWardName == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.mkaWardName);
        }
        if (value.mkaLocation == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindLong(23, value.mkaLocation);
        }
        if (value.mkaLocationName == null) {
          stmt.bindNull(24);
        } else {
          stmt.bindString(24, value.mkaLocationName);
        }
        if (value.workingRollLLGId == null) {
          stmt.bindNull(25);
        } else {
          stmt.bindLong(25, value.workingRollLLGId);
        }
        if (value.workingRollLLGName == null) {
          stmt.bindNull(26);
        } else {
          stmt.bindString(26, value.workingRollLLGName);
        }
        if (value.workingRollWardId == null) {
          stmt.bindNull(27);
        } else {
          stmt.bindLong(27, value.workingRollWardId);
        }
        if (value.workingRollWardName == null) {
          stmt.bindNull(28);
        } else {
          stmt.bindString(28, value.workingRollWardName);
        }
        if (value.workingRollLocationId == null) {
          stmt.bindNull(29);
        } else {
          stmt.bindLong(29, value.workingRollLocationId);
        }
        if (value.workingRollLocationName == null) {
          stmt.bindNull(30);
        } else {
          stmt.bindString(30, value.workingRollLocationName);
        }
        if (value.workingRollMKALLGId == null) {
          stmt.bindNull(31);
        } else {
          stmt.bindLong(31, value.workingRollMKALLGId);
        }
        if (value.workingRollMKALLGName == null) {
          stmt.bindNull(32);
        } else {
          stmt.bindString(32, value.workingRollMKALLGName);
        }
        if (value.workingRollMKAWardId == null) {
          stmt.bindNull(33);
        } else {
          stmt.bindLong(33, value.workingRollMKAWardId);
        }
        if (value.workingRollMKAWardName == null) {
          stmt.bindNull(34);
        } else {
          stmt.bindString(34, value.workingRollMKAWardName);
        }
        if (value.workingRollMKALocationId == null) {
          stmt.bindNull(35);
        } else {
          stmt.bindLong(35, value.workingRollMKALocationId);
        }
        if (value.workingRollMKALocationName == null) {
          stmt.bindNull(36);
        } else {
          stmt.bindString(36, value.workingRollMKALocationName);
        }
      }
    };
    this.__deletionAdapterOfDpoList = new EntityDeletionOrUpdateAdapter<DpoList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `DpoList` WHERE `userId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DpoList value) {
        if (value.userId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.userId);
        }
      }
    };
    this.__updateAdapterOfDpoList = new EntityDeletionOrUpdateAdapter<DpoList>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `DpoList` SET `userId` = ?,`firstName` = ?,`lastName` = ?,`emailAddress` = ?,`region` = ?,`regionName` = ?,`province` = ?,`provinceName` = ?,`district` = ?,`districtName` = ?,`llg` = ?,`llgName` = ?,`ward` = ?,`wardName` = ?,`location` = ?,`locationName` = ?,`mkaDistrict` = ?,`mkaDistrictName` = ?,`mkallg` = ?,`mkallgName` = ?,`mkaWard` = ?,`mkaWardName` = ?,`mkaLocation` = ?,`mkaLocationName` = ?,`workingRollLLGId` = ?,`workingRollLLGName` = ?,`workingRollWardId` = ?,`workingRollWardName` = ?,`workingRollLocationId` = ?,`workingRollLocationName` = ?,`workingRollMKALLGId` = ?,`workingRollMKALLGName` = ?,`workingRollMKAWardId` = ?,`workingRollMKAWardName` = ?,`workingRollMKALocationId` = ?,`workingRollMKALocationName` = ? WHERE `userId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DpoList value) {
        if (value.userId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.userId);
        }
        if (value.firstName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.firstName);
        }
        if (value.lastName == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.lastName);
        }
        if (value.emailAddress == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.emailAddress);
        }
        if (value.region == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.region);
        }
        if (value.regionName == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.regionName);
        }
        if (value.province == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.province);
        }
        if (value.provinceName == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.provinceName);
        }
        if (value.district == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindLong(9, value.district);
        }
        if (value.districtName == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.districtName);
        }
        if (value.llg == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, value.llg);
        }
        if (value.llgName == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.llgName);
        }
        if (value.ward == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindLong(13, value.ward);
        }
        if (value.wardName == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.wardName);
        }
        if (value.location == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindLong(15, value.location);
        }
        if (value.locationName == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.locationName);
        }
        if (value.mkaDistrict == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindLong(17, value.mkaDistrict);
        }
        if (value.mkaDistrictName == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.mkaDistrictName);
        }
        if (value.mkallg == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindLong(19, value.mkallg);
        }
        if (value.mkallgName == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.mkallgName);
        }
        if (value.mkaWard == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindLong(21, value.mkaWard);
        }
        if (value.mkaWardName == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.mkaWardName);
        }
        if (value.mkaLocation == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindLong(23, value.mkaLocation);
        }
        if (value.mkaLocationName == null) {
          stmt.bindNull(24);
        } else {
          stmt.bindString(24, value.mkaLocationName);
        }
        if (value.workingRollLLGId == null) {
          stmt.bindNull(25);
        } else {
          stmt.bindLong(25, value.workingRollLLGId);
        }
        if (value.workingRollLLGName == null) {
          stmt.bindNull(26);
        } else {
          stmt.bindString(26, value.workingRollLLGName);
        }
        if (value.workingRollWardId == null) {
          stmt.bindNull(27);
        } else {
          stmt.bindLong(27, value.workingRollWardId);
        }
        if (value.workingRollWardName == null) {
          stmt.bindNull(28);
        } else {
          stmt.bindString(28, value.workingRollWardName);
        }
        if (value.workingRollLocationId == null) {
          stmt.bindNull(29);
        } else {
          stmt.bindLong(29, value.workingRollLocationId);
        }
        if (value.workingRollLocationName == null) {
          stmt.bindNull(30);
        } else {
          stmt.bindString(30, value.workingRollLocationName);
        }
        if (value.workingRollMKALLGId == null) {
          stmt.bindNull(31);
        } else {
          stmt.bindLong(31, value.workingRollMKALLGId);
        }
        if (value.workingRollMKALLGName == null) {
          stmt.bindNull(32);
        } else {
          stmt.bindString(32, value.workingRollMKALLGName);
        }
        if (value.workingRollMKAWardId == null) {
          stmt.bindNull(33);
        } else {
          stmt.bindLong(33, value.workingRollMKAWardId);
        }
        if (value.workingRollMKAWardName == null) {
          stmt.bindNull(34);
        } else {
          stmt.bindString(34, value.workingRollMKAWardName);
        }
        if (value.workingRollMKALocationId == null) {
          stmt.bindNull(35);
        } else {
          stmt.bindLong(35, value.workingRollMKALocationId);
        }
        if (value.workingRollMKALocationName == null) {
          stmt.bindNull(36);
        } else {
          stmt.bindString(36, value.workingRollMKALocationName);
        }
        if (value.userId == null) {
          stmt.bindNull(37);
        } else {
          stmt.bindLong(37, value.userId);
        }
      }
    };
    this.__preparedStmtOfUpdateDPOWorkingRollLLGId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE dpolist SET workingRollLLGId = ?  WHERE userId ==?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDPOWorkingRollLLGName = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE dpolist SET  workingRollLLGName =?   WHERE userId ==?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDPOWorkingRollWardId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE dpolist SET workingRollWardId =?   WHERE userId ==?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDPOWorkingRollWardName = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE dpolist SET  workingRollWardName =?  WHERE userId ==?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDPOWorkingRollLocationId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE dpolist SET workingRollLocationId =?  WHERE userId ==?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDPOWorkingRollLocationName = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE dpolist SET  workingRollLocationName =?  WHERE userId ==?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  FROM dpolist";
        return _query;
      }
    };
  }

  @Override
  public void insert(DpoList dpo) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfDpoList.insert(dpo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(DpoList dpo) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfDpoList.handle(dpo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(DpoList dpo) {
    __db.beginTransaction();
    try {
      __updateAdapterOfDpoList.handle(dpo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateDPOWorkingRollLLGId(Integer userId, Integer llgId) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDPOWorkingRollLLGId.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (llgId == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, llgId);
      }
      _argIndex = 2;
      if (userId == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, userId);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateDPOWorkingRollLLGId.release(_stmt);
    }
  }

  @Override
  public void updateDPOWorkingRollLLGName(Integer userId, String llgName) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDPOWorkingRollLLGName.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (llgName == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, llgName);
      }
      _argIndex = 2;
      if (userId == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, userId);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateDPOWorkingRollLLGName.release(_stmt);
    }
  }

  @Override
  public void updateDPOWorkingRollWardId(Integer userId, Integer wardId) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDPOWorkingRollWardId.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (wardId == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, wardId);
      }
      _argIndex = 2;
      if (userId == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, userId);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateDPOWorkingRollWardId.release(_stmt);
    }
  }

  @Override
  public void updateDPOWorkingRollWardName(Integer userId, String wardName) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDPOWorkingRollWardName.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (wardName == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, wardName);
      }
      _argIndex = 2;
      if (userId == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, userId);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateDPOWorkingRollWardName.release(_stmt);
    }
  }

  @Override
  public void updateDPOWorkingRollLocationId(Integer userId, Integer locationId) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDPOWorkingRollLocationId.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (locationId == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, locationId);
      }
      _argIndex = 2;
      if (userId == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, userId);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateDPOWorkingRollLocationId.release(_stmt);
    }
  }

  @Override
  public void updateDPOWorkingRollLocationName(Integer userId, String locationName) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDPOWorkingRollLocationName.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (locationName == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, locationName);
      }
      _argIndex = 2;
      if (userId == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, userId);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateDPOWorkingRollLocationName.release(_stmt);
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
  public List<DpoList> getAll() {
    final String _sql = "SELECT * FROM dpolist";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("userId");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("firstName");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("lastName");
      final int _cursorIndexOfEmailAddress = _cursor.getColumnIndexOrThrow("emailAddress");
      final int _cursorIndexOfRegion = _cursor.getColumnIndexOrThrow("region");
      final int _cursorIndexOfRegionName = _cursor.getColumnIndexOrThrow("regionName");
      final int _cursorIndexOfProvince = _cursor.getColumnIndexOrThrow("province");
      final int _cursorIndexOfProvinceName = _cursor.getColumnIndexOrThrow("provinceName");
      final int _cursorIndexOfDistrict = _cursor.getColumnIndexOrThrow("district");
      final int _cursorIndexOfDistrictName = _cursor.getColumnIndexOrThrow("districtName");
      final int _cursorIndexOfLlg = _cursor.getColumnIndexOrThrow("llg");
      final int _cursorIndexOfLlgName = _cursor.getColumnIndexOrThrow("llgName");
      final int _cursorIndexOfWard = _cursor.getColumnIndexOrThrow("ward");
      final int _cursorIndexOfWardName = _cursor.getColumnIndexOrThrow("wardName");
      final int _cursorIndexOfLocation = _cursor.getColumnIndexOrThrow("location");
      final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("locationName");
      final int _cursorIndexOfMkaDistrict = _cursor.getColumnIndexOrThrow("mkaDistrict");
      final int _cursorIndexOfMkaDistrictName = _cursor.getColumnIndexOrThrow("mkaDistrictName");
      final int _cursorIndexOfMkallg = _cursor.getColumnIndexOrThrow("mkallg");
      final int _cursorIndexOfMkallgName = _cursor.getColumnIndexOrThrow("mkallgName");
      final int _cursorIndexOfMkaWard = _cursor.getColumnIndexOrThrow("mkaWard");
      final int _cursorIndexOfMkaWardName = _cursor.getColumnIndexOrThrow("mkaWardName");
      final int _cursorIndexOfMkaLocation = _cursor.getColumnIndexOrThrow("mkaLocation");
      final int _cursorIndexOfMkaLocationName = _cursor.getColumnIndexOrThrow("mkaLocationName");
      final int _cursorIndexOfWorkingRollLLGId = _cursor.getColumnIndexOrThrow("workingRollLLGId");
      final int _cursorIndexOfWorkingRollLLGName = _cursor.getColumnIndexOrThrow("workingRollLLGName");
      final int _cursorIndexOfWorkingRollWardId = _cursor.getColumnIndexOrThrow("workingRollWardId");
      final int _cursorIndexOfWorkingRollWardName = _cursor.getColumnIndexOrThrow("workingRollWardName");
      final int _cursorIndexOfWorkingRollLocationId = _cursor.getColumnIndexOrThrow("workingRollLocationId");
      final int _cursorIndexOfWorkingRollLocationName = _cursor.getColumnIndexOrThrow("workingRollLocationName");
      final int _cursorIndexOfWorkingRollMKALLGId = _cursor.getColumnIndexOrThrow("workingRollMKALLGId");
      final int _cursorIndexOfWorkingRollMKALLGName = _cursor.getColumnIndexOrThrow("workingRollMKALLGName");
      final int _cursorIndexOfWorkingRollMKAWardId = _cursor.getColumnIndexOrThrow("workingRollMKAWardId");
      final int _cursorIndexOfWorkingRollMKAWardName = _cursor.getColumnIndexOrThrow("workingRollMKAWardName");
      final int _cursorIndexOfWorkingRollMKALocationId = _cursor.getColumnIndexOrThrow("workingRollMKALocationId");
      final int _cursorIndexOfWorkingRollMKALocationName = _cursor.getColumnIndexOrThrow("workingRollMKALocationName");
      final List<DpoList> _result = new ArrayList<DpoList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DpoList _item;
        _item = new DpoList();
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _item.userId = null;
        } else {
          _item.userId = _cursor.getInt(_cursorIndexOfUserId);
        }
        _item.firstName = _cursor.getString(_cursorIndexOfFirstName);
        _item.lastName = _cursor.getString(_cursorIndexOfLastName);
        _item.emailAddress = _cursor.getString(_cursorIndexOfEmailAddress);
        if (_cursor.isNull(_cursorIndexOfRegion)) {
          _item.region = null;
        } else {
          _item.region = _cursor.getInt(_cursorIndexOfRegion);
        }
        _item.regionName = _cursor.getString(_cursorIndexOfRegionName);
        if (_cursor.isNull(_cursorIndexOfProvince)) {
          _item.province = null;
        } else {
          _item.province = _cursor.getInt(_cursorIndexOfProvince);
        }
        _item.provinceName = _cursor.getString(_cursorIndexOfProvinceName);
        if (_cursor.isNull(_cursorIndexOfDistrict)) {
          _item.district = null;
        } else {
          _item.district = _cursor.getInt(_cursorIndexOfDistrict);
        }
        _item.districtName = _cursor.getString(_cursorIndexOfDistrictName);
        if (_cursor.isNull(_cursorIndexOfLlg)) {
          _item.llg = null;
        } else {
          _item.llg = _cursor.getInt(_cursorIndexOfLlg);
        }
        _item.llgName = _cursor.getString(_cursorIndexOfLlgName);
        if (_cursor.isNull(_cursorIndexOfWard)) {
          _item.ward = null;
        } else {
          _item.ward = _cursor.getInt(_cursorIndexOfWard);
        }
        _item.wardName = _cursor.getString(_cursorIndexOfWardName);
        if (_cursor.isNull(_cursorIndexOfLocation)) {
          _item.location = null;
        } else {
          _item.location = _cursor.getInt(_cursorIndexOfLocation);
        }
        _item.locationName = _cursor.getString(_cursorIndexOfLocationName);
        if (_cursor.isNull(_cursorIndexOfMkaDistrict)) {
          _item.mkaDistrict = null;
        } else {
          _item.mkaDistrict = _cursor.getInt(_cursorIndexOfMkaDistrict);
        }
        _item.mkaDistrictName = _cursor.getString(_cursorIndexOfMkaDistrictName);
        if (_cursor.isNull(_cursorIndexOfMkallg)) {
          _item.mkallg = null;
        } else {
          _item.mkallg = _cursor.getInt(_cursorIndexOfMkallg);
        }
        _item.mkallgName = _cursor.getString(_cursorIndexOfMkallgName);
        if (_cursor.isNull(_cursorIndexOfMkaWard)) {
          _item.mkaWard = null;
        } else {
          _item.mkaWard = _cursor.getInt(_cursorIndexOfMkaWard);
        }
        _item.mkaWardName = _cursor.getString(_cursorIndexOfMkaWardName);
        if (_cursor.isNull(_cursorIndexOfMkaLocation)) {
          _item.mkaLocation = null;
        } else {
          _item.mkaLocation = _cursor.getInt(_cursorIndexOfMkaLocation);
        }
        _item.mkaLocationName = _cursor.getString(_cursorIndexOfMkaLocationName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollLLGId)) {
          _item.workingRollLLGId = null;
        } else {
          _item.workingRollLLGId = _cursor.getInt(_cursorIndexOfWorkingRollLLGId);
        }
        _item.workingRollLLGName = _cursor.getString(_cursorIndexOfWorkingRollLLGName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollWardId)) {
          _item.workingRollWardId = null;
        } else {
          _item.workingRollWardId = _cursor.getInt(_cursorIndexOfWorkingRollWardId);
        }
        _item.workingRollWardName = _cursor.getString(_cursorIndexOfWorkingRollWardName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollLocationId)) {
          _item.workingRollLocationId = null;
        } else {
          _item.workingRollLocationId = _cursor.getInt(_cursorIndexOfWorkingRollLocationId);
        }
        _item.workingRollLocationName = _cursor.getString(_cursorIndexOfWorkingRollLocationName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKALLGId)) {
          _item.workingRollMKALLGId = null;
        } else {
          _item.workingRollMKALLGId = _cursor.getInt(_cursorIndexOfWorkingRollMKALLGId);
        }
        _item.workingRollMKALLGName = _cursor.getString(_cursorIndexOfWorkingRollMKALLGName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKAWardId)) {
          _item.workingRollMKAWardId = null;
        } else {
          _item.workingRollMKAWardId = _cursor.getInt(_cursorIndexOfWorkingRollMKAWardId);
        }
        _item.workingRollMKAWardName = _cursor.getString(_cursorIndexOfWorkingRollMKAWardName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKALocationId)) {
          _item.workingRollMKALocationId = null;
        } else {
          _item.workingRollMKALocationId = _cursor.getInt(_cursorIndexOfWorkingRollMKALocationId);
        }
        _item.workingRollMKALocationName = _cursor.getString(_cursorIndexOfWorkingRollMKALocationName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public DpoList getDPO(Integer userId) {
    final String _sql = "SELECT * FROM dpolist WHERE userId ==?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, userId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("userId");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("firstName");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("lastName");
      final int _cursorIndexOfEmailAddress = _cursor.getColumnIndexOrThrow("emailAddress");
      final int _cursorIndexOfRegion = _cursor.getColumnIndexOrThrow("region");
      final int _cursorIndexOfRegionName = _cursor.getColumnIndexOrThrow("regionName");
      final int _cursorIndexOfProvince = _cursor.getColumnIndexOrThrow("province");
      final int _cursorIndexOfProvinceName = _cursor.getColumnIndexOrThrow("provinceName");
      final int _cursorIndexOfDistrict = _cursor.getColumnIndexOrThrow("district");
      final int _cursorIndexOfDistrictName = _cursor.getColumnIndexOrThrow("districtName");
      final int _cursorIndexOfLlg = _cursor.getColumnIndexOrThrow("llg");
      final int _cursorIndexOfLlgName = _cursor.getColumnIndexOrThrow("llgName");
      final int _cursorIndexOfWard = _cursor.getColumnIndexOrThrow("ward");
      final int _cursorIndexOfWardName = _cursor.getColumnIndexOrThrow("wardName");
      final int _cursorIndexOfLocation = _cursor.getColumnIndexOrThrow("location");
      final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("locationName");
      final int _cursorIndexOfMkaDistrict = _cursor.getColumnIndexOrThrow("mkaDistrict");
      final int _cursorIndexOfMkaDistrictName = _cursor.getColumnIndexOrThrow("mkaDistrictName");
      final int _cursorIndexOfMkallg = _cursor.getColumnIndexOrThrow("mkallg");
      final int _cursorIndexOfMkallgName = _cursor.getColumnIndexOrThrow("mkallgName");
      final int _cursorIndexOfMkaWard = _cursor.getColumnIndexOrThrow("mkaWard");
      final int _cursorIndexOfMkaWardName = _cursor.getColumnIndexOrThrow("mkaWardName");
      final int _cursorIndexOfMkaLocation = _cursor.getColumnIndexOrThrow("mkaLocation");
      final int _cursorIndexOfMkaLocationName = _cursor.getColumnIndexOrThrow("mkaLocationName");
      final int _cursorIndexOfWorkingRollLLGId = _cursor.getColumnIndexOrThrow("workingRollLLGId");
      final int _cursorIndexOfWorkingRollLLGName = _cursor.getColumnIndexOrThrow("workingRollLLGName");
      final int _cursorIndexOfWorkingRollWardId = _cursor.getColumnIndexOrThrow("workingRollWardId");
      final int _cursorIndexOfWorkingRollWardName = _cursor.getColumnIndexOrThrow("workingRollWardName");
      final int _cursorIndexOfWorkingRollLocationId = _cursor.getColumnIndexOrThrow("workingRollLocationId");
      final int _cursorIndexOfWorkingRollLocationName = _cursor.getColumnIndexOrThrow("workingRollLocationName");
      final int _cursorIndexOfWorkingRollMKALLGId = _cursor.getColumnIndexOrThrow("workingRollMKALLGId");
      final int _cursorIndexOfWorkingRollMKALLGName = _cursor.getColumnIndexOrThrow("workingRollMKALLGName");
      final int _cursorIndexOfWorkingRollMKAWardId = _cursor.getColumnIndexOrThrow("workingRollMKAWardId");
      final int _cursorIndexOfWorkingRollMKAWardName = _cursor.getColumnIndexOrThrow("workingRollMKAWardName");
      final int _cursorIndexOfWorkingRollMKALocationId = _cursor.getColumnIndexOrThrow("workingRollMKALocationId");
      final int _cursorIndexOfWorkingRollMKALocationName = _cursor.getColumnIndexOrThrow("workingRollMKALocationName");
      final DpoList _result;
      if(_cursor.moveToFirst()) {
        _result = new DpoList();
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _result.userId = null;
        } else {
          _result.userId = _cursor.getInt(_cursorIndexOfUserId);
        }
        _result.firstName = _cursor.getString(_cursorIndexOfFirstName);
        _result.lastName = _cursor.getString(_cursorIndexOfLastName);
        _result.emailAddress = _cursor.getString(_cursorIndexOfEmailAddress);
        if (_cursor.isNull(_cursorIndexOfRegion)) {
          _result.region = null;
        } else {
          _result.region = _cursor.getInt(_cursorIndexOfRegion);
        }
        _result.regionName = _cursor.getString(_cursorIndexOfRegionName);
        if (_cursor.isNull(_cursorIndexOfProvince)) {
          _result.province = null;
        } else {
          _result.province = _cursor.getInt(_cursorIndexOfProvince);
        }
        _result.provinceName = _cursor.getString(_cursorIndexOfProvinceName);
        if (_cursor.isNull(_cursorIndexOfDistrict)) {
          _result.district = null;
        } else {
          _result.district = _cursor.getInt(_cursorIndexOfDistrict);
        }
        _result.districtName = _cursor.getString(_cursorIndexOfDistrictName);
        if (_cursor.isNull(_cursorIndexOfLlg)) {
          _result.llg = null;
        } else {
          _result.llg = _cursor.getInt(_cursorIndexOfLlg);
        }
        _result.llgName = _cursor.getString(_cursorIndexOfLlgName);
        if (_cursor.isNull(_cursorIndexOfWard)) {
          _result.ward = null;
        } else {
          _result.ward = _cursor.getInt(_cursorIndexOfWard);
        }
        _result.wardName = _cursor.getString(_cursorIndexOfWardName);
        if (_cursor.isNull(_cursorIndexOfLocation)) {
          _result.location = null;
        } else {
          _result.location = _cursor.getInt(_cursorIndexOfLocation);
        }
        _result.locationName = _cursor.getString(_cursorIndexOfLocationName);
        if (_cursor.isNull(_cursorIndexOfMkaDistrict)) {
          _result.mkaDistrict = null;
        } else {
          _result.mkaDistrict = _cursor.getInt(_cursorIndexOfMkaDistrict);
        }
        _result.mkaDistrictName = _cursor.getString(_cursorIndexOfMkaDistrictName);
        if (_cursor.isNull(_cursorIndexOfMkallg)) {
          _result.mkallg = null;
        } else {
          _result.mkallg = _cursor.getInt(_cursorIndexOfMkallg);
        }
        _result.mkallgName = _cursor.getString(_cursorIndexOfMkallgName);
        if (_cursor.isNull(_cursorIndexOfMkaWard)) {
          _result.mkaWard = null;
        } else {
          _result.mkaWard = _cursor.getInt(_cursorIndexOfMkaWard);
        }
        _result.mkaWardName = _cursor.getString(_cursorIndexOfMkaWardName);
        if (_cursor.isNull(_cursorIndexOfMkaLocation)) {
          _result.mkaLocation = null;
        } else {
          _result.mkaLocation = _cursor.getInt(_cursorIndexOfMkaLocation);
        }
        _result.mkaLocationName = _cursor.getString(_cursorIndexOfMkaLocationName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollLLGId)) {
          _result.workingRollLLGId = null;
        } else {
          _result.workingRollLLGId = _cursor.getInt(_cursorIndexOfWorkingRollLLGId);
        }
        _result.workingRollLLGName = _cursor.getString(_cursorIndexOfWorkingRollLLGName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollWardId)) {
          _result.workingRollWardId = null;
        } else {
          _result.workingRollWardId = _cursor.getInt(_cursorIndexOfWorkingRollWardId);
        }
        _result.workingRollWardName = _cursor.getString(_cursorIndexOfWorkingRollWardName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollLocationId)) {
          _result.workingRollLocationId = null;
        } else {
          _result.workingRollLocationId = _cursor.getInt(_cursorIndexOfWorkingRollLocationId);
        }
        _result.workingRollLocationName = _cursor.getString(_cursorIndexOfWorkingRollLocationName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKALLGId)) {
          _result.workingRollMKALLGId = null;
        } else {
          _result.workingRollMKALLGId = _cursor.getInt(_cursorIndexOfWorkingRollMKALLGId);
        }
        _result.workingRollMKALLGName = _cursor.getString(_cursorIndexOfWorkingRollMKALLGName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKAWardId)) {
          _result.workingRollMKAWardId = null;
        } else {
          _result.workingRollMKAWardId = _cursor.getInt(_cursorIndexOfWorkingRollMKAWardId);
        }
        _result.workingRollMKAWardName = _cursor.getString(_cursorIndexOfWorkingRollMKAWardName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKALocationId)) {
          _result.workingRollMKALocationId = null;
        } else {
          _result.workingRollMKALocationId = _cursor.getInt(_cursorIndexOfWorkingRollMKALocationId);
        }
        _result.workingRollMKALocationName = _cursor.getString(_cursorIndexOfWorkingRollMKALocationName);
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
  public DpoList getDPOUsingEmailAddress(String email) {
    final String _sql = "SELECT * FROM dpolist WHERE emailAddress ==?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("userId");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("firstName");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("lastName");
      final int _cursorIndexOfEmailAddress = _cursor.getColumnIndexOrThrow("emailAddress");
      final int _cursorIndexOfRegion = _cursor.getColumnIndexOrThrow("region");
      final int _cursorIndexOfRegionName = _cursor.getColumnIndexOrThrow("regionName");
      final int _cursorIndexOfProvince = _cursor.getColumnIndexOrThrow("province");
      final int _cursorIndexOfProvinceName = _cursor.getColumnIndexOrThrow("provinceName");
      final int _cursorIndexOfDistrict = _cursor.getColumnIndexOrThrow("district");
      final int _cursorIndexOfDistrictName = _cursor.getColumnIndexOrThrow("districtName");
      final int _cursorIndexOfLlg = _cursor.getColumnIndexOrThrow("llg");
      final int _cursorIndexOfLlgName = _cursor.getColumnIndexOrThrow("llgName");
      final int _cursorIndexOfWard = _cursor.getColumnIndexOrThrow("ward");
      final int _cursorIndexOfWardName = _cursor.getColumnIndexOrThrow("wardName");
      final int _cursorIndexOfLocation = _cursor.getColumnIndexOrThrow("location");
      final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("locationName");
      final int _cursorIndexOfMkaDistrict = _cursor.getColumnIndexOrThrow("mkaDistrict");
      final int _cursorIndexOfMkaDistrictName = _cursor.getColumnIndexOrThrow("mkaDistrictName");
      final int _cursorIndexOfMkallg = _cursor.getColumnIndexOrThrow("mkallg");
      final int _cursorIndexOfMkallgName = _cursor.getColumnIndexOrThrow("mkallgName");
      final int _cursorIndexOfMkaWard = _cursor.getColumnIndexOrThrow("mkaWard");
      final int _cursorIndexOfMkaWardName = _cursor.getColumnIndexOrThrow("mkaWardName");
      final int _cursorIndexOfMkaLocation = _cursor.getColumnIndexOrThrow("mkaLocation");
      final int _cursorIndexOfMkaLocationName = _cursor.getColumnIndexOrThrow("mkaLocationName");
      final int _cursorIndexOfWorkingRollLLGId = _cursor.getColumnIndexOrThrow("workingRollLLGId");
      final int _cursorIndexOfWorkingRollLLGName = _cursor.getColumnIndexOrThrow("workingRollLLGName");
      final int _cursorIndexOfWorkingRollWardId = _cursor.getColumnIndexOrThrow("workingRollWardId");
      final int _cursorIndexOfWorkingRollWardName = _cursor.getColumnIndexOrThrow("workingRollWardName");
      final int _cursorIndexOfWorkingRollLocationId = _cursor.getColumnIndexOrThrow("workingRollLocationId");
      final int _cursorIndexOfWorkingRollLocationName = _cursor.getColumnIndexOrThrow("workingRollLocationName");
      final int _cursorIndexOfWorkingRollMKALLGId = _cursor.getColumnIndexOrThrow("workingRollMKALLGId");
      final int _cursorIndexOfWorkingRollMKALLGName = _cursor.getColumnIndexOrThrow("workingRollMKALLGName");
      final int _cursorIndexOfWorkingRollMKAWardId = _cursor.getColumnIndexOrThrow("workingRollMKAWardId");
      final int _cursorIndexOfWorkingRollMKAWardName = _cursor.getColumnIndexOrThrow("workingRollMKAWardName");
      final int _cursorIndexOfWorkingRollMKALocationId = _cursor.getColumnIndexOrThrow("workingRollMKALocationId");
      final int _cursorIndexOfWorkingRollMKALocationName = _cursor.getColumnIndexOrThrow("workingRollMKALocationName");
      final DpoList _result;
      if(_cursor.moveToFirst()) {
        _result = new DpoList();
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _result.userId = null;
        } else {
          _result.userId = _cursor.getInt(_cursorIndexOfUserId);
        }
        _result.firstName = _cursor.getString(_cursorIndexOfFirstName);
        _result.lastName = _cursor.getString(_cursorIndexOfLastName);
        _result.emailAddress = _cursor.getString(_cursorIndexOfEmailAddress);
        if (_cursor.isNull(_cursorIndexOfRegion)) {
          _result.region = null;
        } else {
          _result.region = _cursor.getInt(_cursorIndexOfRegion);
        }
        _result.regionName = _cursor.getString(_cursorIndexOfRegionName);
        if (_cursor.isNull(_cursorIndexOfProvince)) {
          _result.province = null;
        } else {
          _result.province = _cursor.getInt(_cursorIndexOfProvince);
        }
        _result.provinceName = _cursor.getString(_cursorIndexOfProvinceName);
        if (_cursor.isNull(_cursorIndexOfDistrict)) {
          _result.district = null;
        } else {
          _result.district = _cursor.getInt(_cursorIndexOfDistrict);
        }
        _result.districtName = _cursor.getString(_cursorIndexOfDistrictName);
        if (_cursor.isNull(_cursorIndexOfLlg)) {
          _result.llg = null;
        } else {
          _result.llg = _cursor.getInt(_cursorIndexOfLlg);
        }
        _result.llgName = _cursor.getString(_cursorIndexOfLlgName);
        if (_cursor.isNull(_cursorIndexOfWard)) {
          _result.ward = null;
        } else {
          _result.ward = _cursor.getInt(_cursorIndexOfWard);
        }
        _result.wardName = _cursor.getString(_cursorIndexOfWardName);
        if (_cursor.isNull(_cursorIndexOfLocation)) {
          _result.location = null;
        } else {
          _result.location = _cursor.getInt(_cursorIndexOfLocation);
        }
        _result.locationName = _cursor.getString(_cursorIndexOfLocationName);
        if (_cursor.isNull(_cursorIndexOfMkaDistrict)) {
          _result.mkaDistrict = null;
        } else {
          _result.mkaDistrict = _cursor.getInt(_cursorIndexOfMkaDistrict);
        }
        _result.mkaDistrictName = _cursor.getString(_cursorIndexOfMkaDistrictName);
        if (_cursor.isNull(_cursorIndexOfMkallg)) {
          _result.mkallg = null;
        } else {
          _result.mkallg = _cursor.getInt(_cursorIndexOfMkallg);
        }
        _result.mkallgName = _cursor.getString(_cursorIndexOfMkallgName);
        if (_cursor.isNull(_cursorIndexOfMkaWard)) {
          _result.mkaWard = null;
        } else {
          _result.mkaWard = _cursor.getInt(_cursorIndexOfMkaWard);
        }
        _result.mkaWardName = _cursor.getString(_cursorIndexOfMkaWardName);
        if (_cursor.isNull(_cursorIndexOfMkaLocation)) {
          _result.mkaLocation = null;
        } else {
          _result.mkaLocation = _cursor.getInt(_cursorIndexOfMkaLocation);
        }
        _result.mkaLocationName = _cursor.getString(_cursorIndexOfMkaLocationName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollLLGId)) {
          _result.workingRollLLGId = null;
        } else {
          _result.workingRollLLGId = _cursor.getInt(_cursorIndexOfWorkingRollLLGId);
        }
        _result.workingRollLLGName = _cursor.getString(_cursorIndexOfWorkingRollLLGName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollWardId)) {
          _result.workingRollWardId = null;
        } else {
          _result.workingRollWardId = _cursor.getInt(_cursorIndexOfWorkingRollWardId);
        }
        _result.workingRollWardName = _cursor.getString(_cursorIndexOfWorkingRollWardName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollLocationId)) {
          _result.workingRollLocationId = null;
        } else {
          _result.workingRollLocationId = _cursor.getInt(_cursorIndexOfWorkingRollLocationId);
        }
        _result.workingRollLocationName = _cursor.getString(_cursorIndexOfWorkingRollLocationName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKALLGId)) {
          _result.workingRollMKALLGId = null;
        } else {
          _result.workingRollMKALLGId = _cursor.getInt(_cursorIndexOfWorkingRollMKALLGId);
        }
        _result.workingRollMKALLGName = _cursor.getString(_cursorIndexOfWorkingRollMKALLGName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKAWardId)) {
          _result.workingRollMKAWardId = null;
        } else {
          _result.workingRollMKAWardId = _cursor.getInt(_cursorIndexOfWorkingRollMKAWardId);
        }
        _result.workingRollMKAWardName = _cursor.getString(_cursorIndexOfWorkingRollMKAWardName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKALocationId)) {
          _result.workingRollMKALocationId = null;
        } else {
          _result.workingRollMKALocationId = _cursor.getInt(_cursorIndexOfWorkingRollMKALocationId);
        }
        _result.workingRollMKALocationName = _cursor.getString(_cursorIndexOfWorkingRollMKALocationName);
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
  public List<DpoList> getDistrictWiseDPOList(Integer districtId) {
    final String _sql = "SELECT * FROM dpolist WHERE district ==?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (districtId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, districtId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("userId");
      final int _cursorIndexOfFirstName = _cursor.getColumnIndexOrThrow("firstName");
      final int _cursorIndexOfLastName = _cursor.getColumnIndexOrThrow("lastName");
      final int _cursorIndexOfEmailAddress = _cursor.getColumnIndexOrThrow("emailAddress");
      final int _cursorIndexOfRegion = _cursor.getColumnIndexOrThrow("region");
      final int _cursorIndexOfRegionName = _cursor.getColumnIndexOrThrow("regionName");
      final int _cursorIndexOfProvince = _cursor.getColumnIndexOrThrow("province");
      final int _cursorIndexOfProvinceName = _cursor.getColumnIndexOrThrow("provinceName");
      final int _cursorIndexOfDistrict = _cursor.getColumnIndexOrThrow("district");
      final int _cursorIndexOfDistrictName = _cursor.getColumnIndexOrThrow("districtName");
      final int _cursorIndexOfLlg = _cursor.getColumnIndexOrThrow("llg");
      final int _cursorIndexOfLlgName = _cursor.getColumnIndexOrThrow("llgName");
      final int _cursorIndexOfWard = _cursor.getColumnIndexOrThrow("ward");
      final int _cursorIndexOfWardName = _cursor.getColumnIndexOrThrow("wardName");
      final int _cursorIndexOfLocation = _cursor.getColumnIndexOrThrow("location");
      final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("locationName");
      final int _cursorIndexOfMkaDistrict = _cursor.getColumnIndexOrThrow("mkaDistrict");
      final int _cursorIndexOfMkaDistrictName = _cursor.getColumnIndexOrThrow("mkaDistrictName");
      final int _cursorIndexOfMkallg = _cursor.getColumnIndexOrThrow("mkallg");
      final int _cursorIndexOfMkallgName = _cursor.getColumnIndexOrThrow("mkallgName");
      final int _cursorIndexOfMkaWard = _cursor.getColumnIndexOrThrow("mkaWard");
      final int _cursorIndexOfMkaWardName = _cursor.getColumnIndexOrThrow("mkaWardName");
      final int _cursorIndexOfMkaLocation = _cursor.getColumnIndexOrThrow("mkaLocation");
      final int _cursorIndexOfMkaLocationName = _cursor.getColumnIndexOrThrow("mkaLocationName");
      final int _cursorIndexOfWorkingRollLLGId = _cursor.getColumnIndexOrThrow("workingRollLLGId");
      final int _cursorIndexOfWorkingRollLLGName = _cursor.getColumnIndexOrThrow("workingRollLLGName");
      final int _cursorIndexOfWorkingRollWardId = _cursor.getColumnIndexOrThrow("workingRollWardId");
      final int _cursorIndexOfWorkingRollWardName = _cursor.getColumnIndexOrThrow("workingRollWardName");
      final int _cursorIndexOfWorkingRollLocationId = _cursor.getColumnIndexOrThrow("workingRollLocationId");
      final int _cursorIndexOfWorkingRollLocationName = _cursor.getColumnIndexOrThrow("workingRollLocationName");
      final int _cursorIndexOfWorkingRollMKALLGId = _cursor.getColumnIndexOrThrow("workingRollMKALLGId");
      final int _cursorIndexOfWorkingRollMKALLGName = _cursor.getColumnIndexOrThrow("workingRollMKALLGName");
      final int _cursorIndexOfWorkingRollMKAWardId = _cursor.getColumnIndexOrThrow("workingRollMKAWardId");
      final int _cursorIndexOfWorkingRollMKAWardName = _cursor.getColumnIndexOrThrow("workingRollMKAWardName");
      final int _cursorIndexOfWorkingRollMKALocationId = _cursor.getColumnIndexOrThrow("workingRollMKALocationId");
      final int _cursorIndexOfWorkingRollMKALocationName = _cursor.getColumnIndexOrThrow("workingRollMKALocationName");
      final List<DpoList> _result = new ArrayList<DpoList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DpoList _item;
        _item = new DpoList();
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _item.userId = null;
        } else {
          _item.userId = _cursor.getInt(_cursorIndexOfUserId);
        }
        _item.firstName = _cursor.getString(_cursorIndexOfFirstName);
        _item.lastName = _cursor.getString(_cursorIndexOfLastName);
        _item.emailAddress = _cursor.getString(_cursorIndexOfEmailAddress);
        if (_cursor.isNull(_cursorIndexOfRegion)) {
          _item.region = null;
        } else {
          _item.region = _cursor.getInt(_cursorIndexOfRegion);
        }
        _item.regionName = _cursor.getString(_cursorIndexOfRegionName);
        if (_cursor.isNull(_cursorIndexOfProvince)) {
          _item.province = null;
        } else {
          _item.province = _cursor.getInt(_cursorIndexOfProvince);
        }
        _item.provinceName = _cursor.getString(_cursorIndexOfProvinceName);
        if (_cursor.isNull(_cursorIndexOfDistrict)) {
          _item.district = null;
        } else {
          _item.district = _cursor.getInt(_cursorIndexOfDistrict);
        }
        _item.districtName = _cursor.getString(_cursorIndexOfDistrictName);
        if (_cursor.isNull(_cursorIndexOfLlg)) {
          _item.llg = null;
        } else {
          _item.llg = _cursor.getInt(_cursorIndexOfLlg);
        }
        _item.llgName = _cursor.getString(_cursorIndexOfLlgName);
        if (_cursor.isNull(_cursorIndexOfWard)) {
          _item.ward = null;
        } else {
          _item.ward = _cursor.getInt(_cursorIndexOfWard);
        }
        _item.wardName = _cursor.getString(_cursorIndexOfWardName);
        if (_cursor.isNull(_cursorIndexOfLocation)) {
          _item.location = null;
        } else {
          _item.location = _cursor.getInt(_cursorIndexOfLocation);
        }
        _item.locationName = _cursor.getString(_cursorIndexOfLocationName);
        if (_cursor.isNull(_cursorIndexOfMkaDistrict)) {
          _item.mkaDistrict = null;
        } else {
          _item.mkaDistrict = _cursor.getInt(_cursorIndexOfMkaDistrict);
        }
        _item.mkaDistrictName = _cursor.getString(_cursorIndexOfMkaDistrictName);
        if (_cursor.isNull(_cursorIndexOfMkallg)) {
          _item.mkallg = null;
        } else {
          _item.mkallg = _cursor.getInt(_cursorIndexOfMkallg);
        }
        _item.mkallgName = _cursor.getString(_cursorIndexOfMkallgName);
        if (_cursor.isNull(_cursorIndexOfMkaWard)) {
          _item.mkaWard = null;
        } else {
          _item.mkaWard = _cursor.getInt(_cursorIndexOfMkaWard);
        }
        _item.mkaWardName = _cursor.getString(_cursorIndexOfMkaWardName);
        if (_cursor.isNull(_cursorIndexOfMkaLocation)) {
          _item.mkaLocation = null;
        } else {
          _item.mkaLocation = _cursor.getInt(_cursorIndexOfMkaLocation);
        }
        _item.mkaLocationName = _cursor.getString(_cursorIndexOfMkaLocationName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollLLGId)) {
          _item.workingRollLLGId = null;
        } else {
          _item.workingRollLLGId = _cursor.getInt(_cursorIndexOfWorkingRollLLGId);
        }
        _item.workingRollLLGName = _cursor.getString(_cursorIndexOfWorkingRollLLGName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollWardId)) {
          _item.workingRollWardId = null;
        } else {
          _item.workingRollWardId = _cursor.getInt(_cursorIndexOfWorkingRollWardId);
        }
        _item.workingRollWardName = _cursor.getString(_cursorIndexOfWorkingRollWardName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollLocationId)) {
          _item.workingRollLocationId = null;
        } else {
          _item.workingRollLocationId = _cursor.getInt(_cursorIndexOfWorkingRollLocationId);
        }
        _item.workingRollLocationName = _cursor.getString(_cursorIndexOfWorkingRollLocationName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKALLGId)) {
          _item.workingRollMKALLGId = null;
        } else {
          _item.workingRollMKALLGId = _cursor.getInt(_cursorIndexOfWorkingRollMKALLGId);
        }
        _item.workingRollMKALLGName = _cursor.getString(_cursorIndexOfWorkingRollMKALLGName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKAWardId)) {
          _item.workingRollMKAWardId = null;
        } else {
          _item.workingRollMKAWardId = _cursor.getInt(_cursorIndexOfWorkingRollMKAWardId);
        }
        _item.workingRollMKAWardName = _cursor.getString(_cursorIndexOfWorkingRollMKAWardName);
        if (_cursor.isNull(_cursorIndexOfWorkingRollMKALocationId)) {
          _item.workingRollMKALocationId = null;
        } else {
          _item.workingRollMKALocationId = _cursor.getInt(_cursorIndexOfWorkingRollMKALocationId);
        }
        _item.workingRollMKALocationName = _cursor.getString(_cursorIndexOfWorkingRollMKALocationName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
