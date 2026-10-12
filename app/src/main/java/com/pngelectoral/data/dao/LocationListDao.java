package com.pngelectoral.data.dao;

import com.pngelectoral.ui.model.LocationList;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LocationListDao {

    @Query("SELECT * FROM locationlist")
    List<LocationList> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationList location);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LocationList> location);

    @Delete
    void delete(LocationList location);

    @Update
    void update(LocationList location);


    @Query("SELECT * FROM locationlist WHERE regionId ==:regionId And provinceId ==:provinceId And districtId ==:districtId " +
            "And llgId ==:llgId And wardId ==:wardId ORDER BY locationName DESC")
    List<LocationList> getLocationListOfRegionAndProvinceAndDistrictAndLLGAndWard(Integer regionId, Integer provinceId,
                                                                                  Integer districtId, Integer llgId,
                                                                                  Integer wardId);

    @Query("SELECT * FROM locationlist WHERE wardId ==:wardId ORDER BY locationName DESC")
    List<LocationList> getLocationListOfWard(Integer wardId);

    @Query("SELECT * FROM locationlist WHERE provinceId ==:provinceId And districtId ==:districtId " +
            "And llgId ==:llgId And wardId ==:wardId ORDER BY locationName DESC")
    List<LocationList> getLocationListOfProvinceAndDistrictAndLLGAndWard(Integer provinceId,
                                                                         Integer districtId, Integer llgId,
                                                                         Integer wardId);

    @Query("SELECT * FROM locationlist WHERE districtId ==:districtId " +
            "And llgId ==:llgId And wardId ==:wardId ORDER BY locationId ASC")
    List<LocationList> getLocationListOfDistrictAndLLGAndWard(Integer districtId, Integer llgId, Integer wardId);

    @Query("SELECT * FROM locationlist WHERE locationId ==:locationId ORDER BY LOWER(locationName) DESC")
    LocationList getDPOLocation(Integer locationId);

    @Query("DELETE  FROM locationlist")
    void deleteAll();
}
