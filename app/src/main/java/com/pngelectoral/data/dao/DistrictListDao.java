package com.pngelectoral.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pngelectoral.ui.model.DistrictList;
import com.pngelectoral.ui.model.ProvinceList;

import java.util.List;

@Dao
public interface DistrictListDao {

    @Query("SELECT * FROM districtlist")
    List<DistrictList> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DistrictList district);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DistrictList> district);

    @Delete
    void delete(DistrictList district);

    @Update
    void update(DistrictList district);

    @Query("SELECT * FROM districtlist WHERE provinceId ==:provinceId ORDER BY districtId ASC")
    List<DistrictList> getDistrictOfProvision(Integer provinceId);

    @Query("SELECT * FROM districtlist WHERE regionId ==:regionId And provinceId ==:provinceId ORDER BY districtId ASC")
    List<DistrictList> getDistrictOfRegionAndProvision(Integer regionId, Integer provinceId);

    @Query("SELECT * FROM districtlist WHERE districtId ==:districtId")
    DistrictList getDPODistrict(Integer districtId);

    @Query("DELETE  FROM districtlist")
    void deleteAll();
}
