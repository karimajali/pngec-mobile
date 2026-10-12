package com.pngelectoral.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pngelectoral.ui.model.LLGList;
import com.pngelectoral.ui.model.ProvinceList;
import com.pngelectoral.ui.model.WardList;

import java.util.List;

@Dao
public interface WardListDao {

    @Query("SELECT * FROM wardlist ORDER BY wardNumber ASC")
    List<WardList> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WardList ward);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WardList> ward);

    @Delete
    void delete(WardList ward);

    @Update
    void update(WardList ward);

    @Query("SELECT * FROM wardlist WHERE provinceId ==:provinceId And llgId ==:llgId ORDER BY wardNumber ASC")
    List<WardList> getWardListOfProvisionAndLLG(Integer provinceId, Integer llgId);

    @Query("SELECT * FROM wardlist WHERE provinceId ==:provinceId And districtId ==:districtId And llgId ==:llgId ORDER BY wardNumber ASC")
    List<WardList> getWardListOfProvisionAndDistrictAndLLG(Integer provinceId, Integer districtId, Integer llgId);

    @Query("SELECT * FROM wardlist WHERE districtId ==:districtId And llgId ==:llgId ORDER BY wardNumber ASC")
    List<WardList> getWardListOfDistrictAndLLG(Integer districtId, Integer llgId);

    @Query("SELECT * FROM wardlist WHERE regionId ==:regionId And provinceId ==:provinceId And districtId ==:districtId And llgId ==:llgId ORDER BY wardNumber ASC")
    List<WardList> getWardListOfRegionAndProvinceAndDistrictAndLLG(Integer regionId,Integer provinceId, Integer districtId, Integer llgId);

    @Query("SELECT * FROM wardlist WHERE wardId ==:wardId")
    WardList getDPOWard(Integer wardId);

    @Query("DELETE  FROM wardlist")
    void deleteAll();
}
