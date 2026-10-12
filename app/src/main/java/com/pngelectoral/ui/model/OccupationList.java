package com.pngelectoral.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class OccupationList implements Serializable {

    @PrimaryKey()
    @ColumnInfo(name = "occupationPK")
    public Integer occupationPK;

    @ColumnInfo(name = "occupationName")
    public String occupationName;




}