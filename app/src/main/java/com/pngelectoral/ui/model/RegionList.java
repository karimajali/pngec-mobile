package com.pngelectoral.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class RegionList implements Serializable {

    @PrimaryKey()
    @ColumnInfo(name = "regionId")
    public Integer regionId;

    @ColumnInfo(name = "regionName")
    public String regionName;

    @ColumnInfo(name = "selected")
    public Boolean selected = false;




}