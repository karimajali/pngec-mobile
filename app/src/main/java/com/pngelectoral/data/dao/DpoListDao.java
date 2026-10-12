package com.pngelectoral.data.dao;

import com.pngelectoral.ui.model.DpoList;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DpoListDao {

    @Query("SELECT * FROM dpolist")
    List<DpoList> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DpoList dpo);

    @Delete
    void delete(DpoList dpo);

    @Update
    void update(DpoList dpo);

    @Query("SELECT * FROM dpolist WHERE userId ==:userId")
    DpoList getDPO(Integer userId);

    @Query("SELECT * FROM dpolist WHERE emailAddress ==:email")
    DpoList getDPOUsingEmailAddress(String email);

    @Query("UPDATE dpolist SET workingRollLLGId = :llgId  WHERE userId ==:userId")
    void updateDPOWorkingRollLLGId(Integer userId, Integer llgId);

    @Query("UPDATE dpolist SET  workingRollLLGName =:llgName   WHERE userId ==:userId")
    void updateDPOWorkingRollLLGName(Integer userId, String llgName);

    @Query("UPDATE dpolist SET workingRollWardId =:wardId   WHERE userId ==:userId")
    void updateDPOWorkingRollWardId(Integer userId, Integer wardId);

    @Query("UPDATE dpolist SET  workingRollWardName =:wardName  WHERE userId ==:userId")
    void updateDPOWorkingRollWardName(Integer userId, String wardName);

    @Query("UPDATE dpolist SET workingRollLocationId =:locationId  WHERE userId ==:userId")
    void updateDPOWorkingRollLocationId(Integer userId, Integer locationId);

    @Query("UPDATE dpolist SET  workingRollLocationName =:locationName  WHERE userId ==:userId")
    void updateDPOWorkingRollLocationName(Integer userId, String locationName);

    @Query("SELECT * FROM dpolist WHERE district ==:districtId")
    List<DpoList> getDistrictWiseDPOList(Integer districtId);

    @Query("DELETE  FROM dpolist")
    void deleteAll();
}
