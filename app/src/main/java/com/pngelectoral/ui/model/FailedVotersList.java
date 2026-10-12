package com.pngelectoral.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class FailedVotersList implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tempElectorId")
    public Integer tempElectorId;

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

    @ColumnInfo(name = "religion")
    public String religion;

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
    public String districtName;

    @ColumnInfo(name = "placeOfBirth")
    public String placeOfBirth;

    @ColumnInfo(name = "occupationFK")
    public Integer occupationFK;

    @ColumnInfo(name = "occupation")
    public String occupation;

    @ColumnInfo(name = "clanFK")
    public Integer clanFK;

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

    @ColumnInfo(name = "electorProfileImageUrl")
    public String electorProfileImageUrl;

    @ColumnInfo(name = "electorFormImage")
    public String electorFormImage;

    @ColumnInfo(name = "electorFormImageUrl")
    public String electorFormImageUrl;

    @ColumnInfo(name = "enrolmentFormNumber")
    public String enrolmentFormNumber;

    @ColumnInfo(name = "createdBy")
    public Integer createdBy;

    @ColumnInfo(name = "isSyncToServer")
    public Integer isSyncToServer = 1;

    @ColumnInfo(name = "isDataUploaded")
    public Integer isDataUploaded = 1;

    @ColumnInfo(name = "isImageUploaded")
    public Integer isImageUploaded = 1;

    @ColumnInfo(name = "updatedDateTime")
    public Integer updatedDateTime = 1;

    @ColumnInfo(name = "syncStatus")
    public Integer syncStatus = 0;

    @ColumnInfo(name = "rejectRecordUpdated")
    public Integer rejectRecordUpdated = 0;

    @ColumnInfo(name = "syncDateTime")
    public String syncDateTime;

    @ColumnInfo(name = "duplicateId")
    public Long duplicateId;

    @ColumnInfo(name = "transferOutRegionId")
    public Integer transferOutRegionId;

    @ColumnInfo(name = "transferOutProvinceId")
    public Integer transferOutProvinceId;

    @ColumnInfo(name = "transferOutDistrictId")
    public Integer transferOutDistrictId;

    @ColumnInfo(name = "transferOutLLGId")
    public Integer transferOutLLGId;

    @ColumnInfo(name = "transferOutWardId")
    public Integer transferOutWardId;

    @ColumnInfo(name = "transferOutLocationId")
    public Integer transferOutLocationId;

    @ColumnInfo(name = "transferInRegionId")
    public Integer transferInRegionId;

    @ColumnInfo(name = "transferInProvinceId")
    public Integer transferInProvinceId;

    @ColumnInfo(name = "transferInDistrictId")
    public Integer transferInDistrictId;

    @ColumnInfo(name = "transferInLLGId")
    public Integer transferInLLGId;

    @ColumnInfo(name = "transferInWardId")
    public Integer transferInWardId;

    @ColumnInfo(name = "transferInLocationId")
    public Integer transferInLocationId;

    @ColumnInfo(name = "errorMessage")
    public String errorMessage;

    @ColumnInfo(name = "rejectedReason")
    public String rejectedReason;

    @ColumnInfo(name = "isRejected")
    public Boolean isRejected = false;

    @ColumnInfo(name = "clanId")
    public Integer clanId;

    @ColumnInfo(name = "clanName")
    public String clanName;

}