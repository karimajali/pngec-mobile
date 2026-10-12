package com.pngelectoral.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pngelectoral.ui.model.ClanListData;
import com.pngelectoral.ui.model.GeoListData;

import java.util.ArrayList;
import java.util.List;

/**Created for entries in table(clanlist) */

@Dao
public interface GeoClanListDao {

    @Query("SELECT * FROM clanlist")
    List<ClanListData> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ClanListData clanModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClanListData> clanList);

    @Delete
    void delete(ClanListData clanModel);

    @Update
    void update(ClanListData clanModel);

    @Query("DELETE FROM clanlist")
    void deleteAll();

    @Query("SELECT * FROM clanlist WHERE clanId == :id ORDER BY LOWER(clanName) DESC")
    ClanListData getDataForClan(Integer id);

    @Query("SELECT * FROM clanlist WHERE wardId == :id ORDER BY LOWER(clanName) DESC")
    List<ClanListData> getDataForClanByWard(Integer id);
}
