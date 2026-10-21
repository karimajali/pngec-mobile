package com.pngelectoral.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewVotersList {

    @SerializedName("rowNo")
    @Expose
    public Integer rowNo;
    @SerializedName("voterName")
    @Expose
    public String voterName;
    @SerializedName("electorId")
    @Expose
    public Long electorId;
    @SerializedName("oldElectorID")
    @Expose
    public Long oldElectorID;
    @SerializedName("locationId")
    @Expose
    public Integer locationId;
    @SerializedName("wardId")
    @Expose
    public Integer wardId;
    @SerializedName("llgId")
    @Expose
    public Integer llgId;
    @SerializedName("districtId")
    @Expose
    public Integer districtId;
    @SerializedName("provinceId")
    @Expose
    public Integer provinceId;
    @SerializedName("regionId")
    @Expose
    public Integer regionId;
    @SerializedName("lName")
    @Expose
    public String lName;
    @SerializedName("fName")
    @Expose
    public String fName;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("gen")
    @Expose
    public String gen;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("dateOfBirth")
    @Expose
    public String dateOfBirth;
    @SerializedName("isYOB")
    @Expose
    public Boolean isYOB;
    @SerializedName("isPWD")
    @Expose
    public Boolean isPWD;
    @SerializedName("fatherLName")
    @Expose
    public String fatherLName;
    @SerializedName("fatherFName")
    @Expose
    public String fatherFName;
    @SerializedName("motherLName")
    @Expose
    public String motherLName;
    @SerializedName("motherFName")
    @Expose
    public String motherFName;
    @SerializedName("provOfBirthFK")
    @Expose
    public Integer provOfBirthFK;
    @SerializedName("provinceOfBirth")
    @Expose
    public String provinceOfBirth;
    @SerializedName("openElectorate")
    @Expose
    public String openElectorate;
    @SerializedName("placeOfBirth")
    @Expose
    public String placeOfBirth;
    @SerializedName("occupationFK")
    @Expose
    public Integer occupationFK;
    @SerializedName("occupation")
    @Expose
    public String occupation;
    @SerializedName("religion")
    @Expose
    public String religion;
    @SerializedName("clanFK")
    @Expose
    public Integer clanFK;
    @SerializedName("clan")
    @Expose
    public String clan;
    @SerializedName("subClan")
    @Expose
    public String subClan;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("natID")
    @Expose
    public String natID;
    @SerializedName("natIDRecordNo")
    @Expose
    public String natIDRecordNo;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("provinceName")
    @Expose
    public String provinceName;
    @SerializedName("districtName")
    @Expose
    public String districtName;
    @SerializedName("llgName")
    @Expose
    public String llgName;
    @SerializedName("wardName")
    @Expose
    public String wardName;
    @SerializedName("locationName")
    @Expose
    public String locationName;
    @SerializedName("houseFK")
    @Expose
    public Integer houseFK;
    @SerializedName("houseSortOrder")
    @Expose
    public Integer houseSortOrder;
    @SerializedName("restHouse")
    @Expose
    public String restHouse;
    @SerializedName("sectionNo")
    @Expose
    public Integer sectionNo;
    @SerializedName("lotNo")
    @Expose
    public Integer lotNo;
    @SerializedName("streetName")
    @Expose
    public String streetName;
    @SerializedName("suburbTownSettlement")
    @Expose
    public String suburbTownSettlement;
    @SerializedName("familyFK")
    @Expose
    public Integer familyFK;
    @SerializedName("familySortOrder")
    @Expose
    public Integer familySortOrder;
    @SerializedName("familyNumber")
    @Expose
    public Integer familyNumber;
    @SerializedName("familyName")
    @Expose
    public String familyName;
    @SerializedName("dpo")
    @Expose
    public String dpo;
    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("long")
    @Expose
    public Double Long;
    @SerializedName("posted")
    @Expose
    public String posted;
    @SerializedName("comment")
    @Expose
    public String comment;
    @SerializedName("electorProfileImage")
    @Expose
    public String electorProfileImage;
    @SerializedName("electorProfileImageUrl")
    @Expose
    public String electorProfileImageUrl;
    @SerializedName("electorFormImage")
    @Expose
    public String electorFormImage;
    @SerializedName("electorFormImageUrl")
    @Expose
    public String electorFormImageUrl;
    @SerializedName("electorProfileImageFile")
    @Expose
    public String electorProfileImageFile;
    @SerializedName("electorFormImageFile")
    @Expose
    public String electorFormImageFile;
    @SerializedName("enrolmentFormNumber")
    @Expose
    public String enrolmentFormNumber;
    @SerializedName("createdBy")
    @Expose
    public Integer createdBy;
    @SerializedName("tempElectorId")
    @Expose
    public Integer tempElectorId;
    @SerializedName("duplicateId")
    @Expose
    public Integer duplicateId;
    @SerializedName("auditDescription")
    @Expose
    public String auditDescription;
    @SerializedName("hasTransferInForm11")
    @Expose
    public Boolean hasTransferInForm11;
    @SerializedName("transferInRegionId")
    @Expose
    public Integer transferInRegionId;
    @SerializedName("transferInProvinceId")
    @Expose
    public Integer transferInProvinceId;
    @SerializedName("transferInDistrictId")
    @Expose
    public Integer transferInDistrictId;
    @SerializedName("transferInLLGId")
    @Expose
    public Integer transferInLLGId;
    @SerializedName("transferInWardId")
    @Expose
    public Integer transferInWardId;
    @SerializedName("transferInLocationId")
    @Expose
    public Integer transferInLocationId;
    @SerializedName("transferProvinceName")
    @Expose
    public String transferProvinceName;
    @SerializedName("transferDistrictName")
    @Expose
    public String transferDistrictName;
    @SerializedName("transferLLGName")
    @Expose
    public String transferLLGName;
    @SerializedName("transferWardName")
    @Expose
    public String transferWardName;
    @SerializedName("transferLocationName")
    @Expose
    public String transferLocationName;
    @SerializedName("isRejectedByRD")
    @Expose
    public Integer isRejectedByRD;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("errorMessage")
    @Expose
    public String errorMessage;
    @SerializedName("arLat")
    @Expose
    public Double arLat;
    @SerializedName("arLong")
    @Expose
    public Double arLong;
    @SerializedName("rejectedReason")
    @Expose
    private String rejectedReason;
    @SerializedName("isApproveOrReject")
    @Expose
    private String isApproveOrReject;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("modifiedByName")
    @Expose
    private String modifiedByName;
    @SerializedName("isRejected")
    @Expose
    private Boolean isRejected;
    @SerializedName("duplicateElectorId")
    @Expose
    private Integer duplicateElectorId;
    @SerializedName("duplicateLName")
    @Expose
    private String duplicateLName;
    @SerializedName("duplicateFName")
    @Expose
    private String duplicateFName;
    @SerializedName("duplicateVoterName")
    @Expose
    private String duplicateVoterName;
    @SerializedName("duplicateGender")
    @Expose
    private String duplicateGender;
    @SerializedName("duplicateGen")
    @Expose
    private String duplicateGen;
    @SerializedName("duplicateDOB")
    @Expose
    private String duplicateDOB;
    @SerializedName("duplicateDateOfBirth")
    @Expose
    private String duplicateDateOfBirth;
    @SerializedName("duplicateOccupation")
    @Expose
    private String duplicateOccupation;
    @SerializedName("duplicateProvinceName")
    @Expose
    private String duplicateProvinceName;
    @SerializedName("duplicateDistrictName")
    @Expose
    private String duplicateDistrictName;
    @SerializedName("duplicateLLGName")
    @Expose
    private String duplicateLLGName;
    @SerializedName("duplicateWardName")
    @Expose
    private String duplicateWardName;
    @SerializedName("duplicateLocationName")
    @Expose
    private String duplicateLocationName;
    @SerializedName("recordType")
    @Expose
    private String recordType;
    @SerializedName("duplicateRecordType")
    @Expose
    private String duplicateRecordType;
    @SerializedName("electorSignatureImage")
    @Expose
    private String electorSignatureImage;
    @SerializedName("electorFingerPrintImage")
    @Expose
    private String electorFingerPrintImage;
    @SerializedName("electorProfileImageBinary")
    @Expose
    private String electorProfileImageBinary;
    @SerializedName("electorSignatureImageBinary")
    @Expose
    private String electorSignatureImageBinary;
    @SerializedName("oldLocationId")
    @Expose
    public Integer oldLocationId;
    @SerializedName("isManualFormNumber")
    @Expose
    public Boolean isManualFormNumber;
    /**Added the new fields appearing with voter's data in downloaded json file.*/
    @SerializedName("mkaDistrictId")
    @Expose
    public Integer mkaDistrictId;
    @SerializedName("mkaDistrictName")
    @Expose
    public String mkaDistrictName;
    @SerializedName("mkallgId")
    @Expose
    public Integer mkallgId;
    @SerializedName("mkallgName")
    @Expose
    public String mkallgName;
    @SerializedName("mkaWardId")
    @Expose
    public Integer mkaWardId;
    @SerializedName("mkaWardName")
    @Expose
    public String mkaWardName;
    @SerializedName("mkaLocationId")
    @Expose
    public Integer mkaLocationId;
    @SerializedName("mkaLocationName")
    @Expose
    public String mkaLocationName;
    @SerializedName("mkaClanId")
    @Expose
    public Integer mkaClanId;
    @SerializedName("mkaClanName")
    @Expose
    public String mkaClanName;
    @SerializedName("clanId")
    @Expose
    public Integer clanId;
    @SerializedName("clanName")
    @Expose
    public String clanName;
    @SerializedName("sevisPassId")
    @Expose
    public String sevisPassId;
    @SerializedName("sevisPassStatus")
    @Expose
    public String sevisPassStatus;

}