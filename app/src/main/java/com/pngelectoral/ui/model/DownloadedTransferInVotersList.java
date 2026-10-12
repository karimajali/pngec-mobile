package com.pngelectoral.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class DownloadedTransferInVotersList implements Serializable {

    @ColumnInfo(name = "rowNo")
    public Long rowNo;

    @ColumnInfo(name = "electorId")
    public Long electorId;

    @ColumnInfo(name = "oldElectorID")
    public Long oldElectorID;

    @ColumnInfo(name = "locationId")
    public Integer locationId;

    @ColumnInfo(name = "locationName")
    public String locationName;

    @ColumnInfo(name = "wardId")
    public Integer wardId;

    @ColumnInfo(name = "wardName")
    public String wardName;

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

    @ColumnInfo(name = "lName")
    public String lName;

    @ColumnInfo(name = "fName")
    public String fName;

    @ColumnInfo(name = "voterName")
    public String voterName;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "dob")
    public String dob;

    @ColumnInfo(name = "isYOB")
    public Boolean isYOB;

    @ColumnInfo(name = "isPWD")
    public Boolean isPWD;

    @ColumnInfo(name = "fatherLName")
    public String fatherLName;

    @ColumnInfo(name = "fatherFName")
    public String fatherFName;

    @ColumnInfo(name = "motherLName")
    public String motherLName;

    @ColumnInfo(name = "motherFName")
    public String motherFName;

    @ColumnInfo(name = "provOfBirthFK")
    public Integer provOfBirthFK;

    @ColumnInfo(name = "provinceOfBirth")
    public String provinceOfBirth;

    @ColumnInfo(name = "provinceName")
    public String provinceName;

    @ColumnInfo(name = "districtName")
    public String openElectorate;

    @ColumnInfo(name = "placeOfBirth")
    public String placeOfBirth;

    @ColumnInfo(name = "occupationFK")
    public Integer occupationFK;

    @ColumnInfo(name = "occupation")
    public String occupation;

    @ColumnInfo(name = "clan")
    public String clan;

    @ColumnInfo(name = "subClan")
    public String subClan;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "natID")
    public String natID;

    @ColumnInfo(name = "natIDRecordNo")
    public String natIDRecordNo;

    @ColumnInfo(name = "status")
    public String status;

    @ColumnInfo(name = "houseFK")
    public Integer houseFK;

    @ColumnInfo(name = "houseSortOrder")
    public Integer houseSortOrder;

    @ColumnInfo(name = "restHouse")
    public String restHouse;

    @ColumnInfo(name = "sectionNo")
    public Integer sectionNo;

    @ColumnInfo(name = "lotNo")
    public Integer lotNo;

    @ColumnInfo(name = "streetName")
    public String streetName;

    @ColumnInfo(name = "suburbTownSettlement")
    public String suburbTownSettlement;

    @ColumnInfo(name = "familyFK")
    public Integer familyFK;

    @ColumnInfo(name = "familySortOrder")
    public Integer familySortOrder;

    @ColumnInfo(name = "familyNumber")
    public Integer familyNumber;

    @ColumnInfo(name = "familyName")
    public String familyName;

    @ColumnInfo(name = "dpo")
    public String dpo;

    @ColumnInfo(name = "lat")
    public Double lat;

    @ColumnInfo(name = "long")
    public Double longitude;

    @ColumnInfo(name = "posted")
    public String posted;

    @ColumnInfo(name = "comment")
    public String comment;

    @ColumnInfo(name = "electorProfileImage")
    public String electorProfileImage;

    @ColumnInfo(name = "electorProfileImageUrl")
    public String electorProfileImageUrl;

    @ColumnInfo(name = "electorFormImage")
    public String electorFormImage;

    @ColumnInfo(name = "electorFormImageUrl")
    public String electorFormImageUrl;

    @ColumnInfo(name = "electorProfileImageFile")
    public String electorProfileImageFile;

    @ColumnInfo(name = "electorFormImageFile")
    public String electorFormImageFile;

    @ColumnInfo(name = "enrolmentFormNumber")
    public String enrolmentFormNumber;

    @ColumnInfo(name = "createdBy")
    public Integer createdBy;


}