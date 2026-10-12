package com.pngelectoral.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pngelectoral.ui.model.LocationList;
import com.pngelectoral.ui.model.MKAGeoListData;
import com.pngelectoral.ui.model.MKAGeoListModel;

import java.util.List;

/**Created for entries in table(mkageolist) */

@Dao
public interface GeoListDao {

    @Query("SELECT * FROM mkageolist")
    List<MKAGeoListData> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MKAGeoListData districtModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MKAGeoListData> districtList);

    @Delete
    void delete(MKAGeoListData location);

    @Update
    void update(MKAGeoListData location);

    @Query("DELETE FROM mkageolist")
    void deleteAll();

    @Query("SELECT * FROM mkageolist WHERE districtId == :id")
    MKAGeoListData getDataForDistrict(Integer id);
}
