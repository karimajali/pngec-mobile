package com.pngelectoral.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pngelectoral.ui.model.OccupationList;
import com.pngelectoral.ui.model.ProvinceList;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface OccupationListDao {

    @Query("SELECT * FROM occupationlist ORDER BY occupationName ASC")
    List<OccupationList> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OccupationList occupation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OccupationList> occupation);

    @Delete
    void delete(OccupationList occupation);

    @Update
    void update(OccupationList occupation);

    @Query("SELECT * FROM occupationlist WHERE occupationPK ==:occupationID")
    OccupationList getOccupation(Integer occupationID);

    @Query("DELETE  FROM occupationlist")
    void deleteAll();
}
