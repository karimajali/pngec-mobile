package com.pngelectoral.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ProvinceList implements Serializable {

    @PrimaryKey()
    @ColumnInfo(name = "provinceId")
    public Integer provinceId;

    @ColumnInfo(name = "provinceName")
    public String provinceName;

    @ColumnInfo(name = "regionId")
    public Integer regionId;




}