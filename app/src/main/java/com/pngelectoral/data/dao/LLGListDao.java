package com.pngelectoral.data.dao;

import com.pngelectoral.ui.model.LLGList;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LLGListDao {

    @Query("SELECT * FROM llglist")
    List<LLGList> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LLGList llg);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LLGList> llg);

    @Delete
    void delete(LLGList llg);

    @Update
    void update(LLGList llg);

    @Query("SELECT * FROM llglist WHERE provinceId ==:provinceId ORDER BY llgId ASC")
    List<LLGList> getLLGOfProvision(Integer provinceId);

    @Query("SELECT * FROM llglist WHERE provinceId ==:provinceId And districtId==:districtId ORDER BY llgId ASC")
    List<LLGList> getLLGOfProvisionAndDistrict(Integer provinceId, Integer districtId);

    @Query("SELECT * FROM llglist WHERE  districtId==:districtId ORDER BY llgId ASC")
    List<LLGList> getLLGOfDistrict(Integer districtId);

    @Query("SELECT * FROM llglist WHERE regionId ==:regionId And provinceId ==:provinceId And districtId==:districtId ORDER BY llgId ASC")
    List<LLGList> getLLGOfRegionAndProvisionAndDistrict(Integer regionId, Integer provinceId, Integer districtId);

    @Query("SELECT * FROM llglist WHERE llgId ==:llgId")
    LLGList getDPOLLG(Integer llgId);

    @Query("DELETE  FROM llglist")
    void deleteAll();
}
