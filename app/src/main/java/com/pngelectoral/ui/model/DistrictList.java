package com.pngelectoral.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class DistrictList implements Serializable {

    @PrimaryKey()
    @ColumnInfo(name = "districtId")
    public Integer districtId;

    @ColumnInfo(name = "districtName")
    public String districtName;

    @ColumnInfo(name = "provinceId")
    public Integer provinceId;

    @ColumnInfo(name = "regionId")
    public Integer regionId;




}