package com.pngelectoral.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pngelectoral.ui.model.MKAClanListData;
import com.pngelectoral.ui.model.MKAGeoListData;

import java.util.List;

/**Created for entries in table(mkaclanlist) */

@Dao
public interface ClanListDao {

    @Query("SELECT * FROM mkaclanlist")
    List<MKAClanListData> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MKAClanListData clanModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MKAClanListData> clanList);

    @Delete
    void delete(MKAClanListData clanModel);

    @Update
    void update(MKAClanListData clanModel);

    @Query("DELETE FROM mkaclanlist")
    void deleteAll();

    @Query("SELECT * FROM mkaclanlist WHERE clanId == :id")
    MKAClanListData getDataForClan(Integer id);
}
