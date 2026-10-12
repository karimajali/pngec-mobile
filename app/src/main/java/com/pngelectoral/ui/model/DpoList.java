package com.pngelectoral.ui.model;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DpoList implements Serializable {

    @PrimaryKey()
    @ColumnInfo(name = "userId")
    public Integer userId;

    @ColumnInfo(name = "firstName")
    public String firstName;

    @ColumnInfo(name = "lastName")
    public String lastName;

    @ColumnInfo(name = "emailAddress")
    public String emailAddress;

    @ColumnInfo(name = "region")
    public Integer region;

    @ColumnInfo(name = "regionName")
    public String regionName;

    @ColumnInfo(name = "province")
    public Integer province;

    @ColumnInfo(name = "provinceName")
    public String provinceName;

    @ColumnInfo(name = "district")
    public Integer district;

    @ColumnInfo(name = "districtName")
    public String districtName;

    @ColumnInfo(name = "llg")
    public Integer llg;

    @ColumnInfo(name = "llgName")
    public String llgName;

    @ColumnInfo(name = "ward")
    public Integer ward;

    @ColumnInfo(name = "wardName")
    public String wardName;

    @ColumnInfo(name = "location")
    public Integer location;

    @ColumnInfo(name = "locationName")
    public String locationName;

    @ColumnInfo(name = "mkaDistrict")
    public Integer mkaDistrict;

    @ColumnInfo(name = "mkaDistrictName")
    public String mkaDistrictName;

    @ColumnInfo(name = "mkallg")
    public Integer mkallg;

    @ColumnInfo(name = "mkallgName")
    public String mkallgName;

    @ColumnInfo(name = "mkaWard")
    public Integer mkaWard;

    @ColumnInfo(name = "mkaWardName")
    public String mkaWardName;

    @ColumnInfo(name = "mkaLocation")
    public Integer mkaLocation;

    @ColumnInfo(name = "mkaLocationName")
    public String mkaLocationName;

    @ColumnInfo(name = "workingRollLLGId")
    public Integer workingRollLLGId = 0;

    @ColumnInfo(name = "workingRollLLGName")
    public String workingRollLLGName = "";

    @ColumnInfo(name = "workingRollWardId")
    public Integer workingRollWardId = 0;

    @ColumnInfo(name = "workingRollWardName")
    public String workingRollWardName = "";

    @ColumnInfo(name = "workingRollLocationId")
    public Integer workingRollLocationId = 0;

    @ColumnInfo(name = "workingRollLocationName")
    public String workingRollLocationName = "";

    @ColumnInfo(name = "workingRollMKALLGId")
    public Integer workingRollMKALLGId = 0;

    @ColumnInfo(name = "workingRollMKALLGName")
    public String workingRollMKALLGName = "";

    @ColumnInfo(name = "workingRollMKAWardId")
    public Integer workingRollMKAWardId = 0;

    @ColumnInfo(name = "workingRollMKAWardName")
    public String workingRollMKAWardName = "";

    @ColumnInfo(name = "workingRollMKALocationId")
    public Integer workingRollMKALocationId = 0;

    @ColumnInfo(name = "workingRollMKALocationName")
    public String workingRollMKALocationName = "";

}