package com.pngelectoral.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class LocationList implements Serializable {

    @PrimaryKey()
    @ColumnInfo(name = "locationId")
    public Integer locationId;

    @ColumnInfo(name = "locationName")
    public String locationName;

    @ColumnInfo(name = "llgId")
    public Integer llgId;

    @ColumnInfo(name = "districtId")
    public Integer districtId;

    @ColumnInfo(name = "provinceId")
    public Integer provinceId;

    @ColumnInfo(name = "regionId")
    public Integer regionId;

    @ColumnInfo(name = "wardId")
    public Integer wardId;




}