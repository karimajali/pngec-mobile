package com.pngelectoral.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class LLGList implements Serializable {

    @PrimaryKey()
    @ColumnInfo(name = "llgId")
    public Integer llgId;

    @ColumnInfo(name = "llgName")
    public String llgName;

    @ColumnInfo(name = "districtId")
    public Integer districtId;

    @ColumnInfo(name = "provinceId")
    public Integer provinceId;

    @ColumnInfo(name = "regionId")
    public Integer regionId;




}