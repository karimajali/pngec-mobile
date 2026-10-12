package com.pngelectoral.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pngelectoral.ui.model.ProvinceList;
import com.pngelectoral.ui.model.RegionList;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProvinceListDao {

    @Query("SELECT * FROM provincelist")
    List<ProvinceList> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProvinceList province);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProvinceList> province);

    @Delete
    void delete(ProvinceList province);

    @Update
    void update(ProvinceList province);

    @Query("SELECT * FROM provincelist WHERE regionId ==:regionId ORDER BY provinceId ASC")
    List<ProvinceList> getProvinceOfRegion(Integer regionId);

    @Query("SELECT * FROM provincelist WHERE provinceId ==:provinceId")
    ProvinceList getDPOProvince(Integer provinceId);

    @Query("DELETE  FROM provincelist")
    void deleteAll();
}
