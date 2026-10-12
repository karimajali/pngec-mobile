package com.pngelectoral.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pngelectoral.ui.model.RegionList;
import com.pngelectoral.ui.model.VotersList;

import java.util.List;

@Dao
public interface RegionListDao {

    @Query("SELECT * FROM regionlist")
    List<RegionList> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RegionList region);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RegionList> region);

    @Delete
    void delete(RegionList region);

    @Update
    void update(RegionList region);

    @Query("SELECT * FROM regionlist WHERE regionId ==:regionId")
    RegionList getDPORegion(Integer regionId);

    @Query("DELETE  FROM regionlist")
    void deleteAll();
}
