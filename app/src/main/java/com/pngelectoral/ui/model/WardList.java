package com.pngelectoral.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class WardList implements Serializable {

    @PrimaryKey()
    @ColumnInfo(name = "wardId")
    public Integer wardId;

    @ColumnInfo(name = "wardName")
    public String wardName;

    @ColumnInfo(name = "wardNumber")
    public Integer wardNumber;

    @ColumnInfo(name = "llgId")
    public Integer llgId;

    @ColumnInfo(name = "districtId")
    public Integer districtId;

    @ColumnInfo(name = "provinceId")
    public Integer provinceId;

    @ColumnInfo(name = "regionId")
    public Integer regionId;




}