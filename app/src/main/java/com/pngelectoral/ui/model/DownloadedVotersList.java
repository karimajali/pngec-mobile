package com.pngelectoral.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadedVotersList {

    @SerializedName("RowNo")
    @Expose
    public Integer RowNo;
    @SerializedName("VoterName")
    @Expose
    public String VoterName;
    @SerializedName("ElectorId")
    @Expose
    public Long ElectorId;
    @SerializedName("OldElectorID")
    @Expose
    public Long OldElectorID;
    @SerializedName("LocationId")
    @Expose
    public Integer LocationId;
    @SerializedName("WardId")
    @Expose
    public Integer WardId;
    @SerializedName("LLGId")
    @Expose
    public Integer LLGId;
    @SerializedName("DistrictId")
    @Expose
    public Integer DistrictId;
    @SerializedName("ProvinceId")
    @Expose
    public Integer ProvinceId;
    @SerializedName("RegionId")
    @Expose
    public Integer RegionId;
    @SerializedName("LName")
    @Expose
    public String LName;
    @SerializedName("FName")
    @Expose
    public String FName;
    @SerializedName("Gender")
    @Expose
    public String Gender;
    @SerializedName("Gen")
    @Expose
    public String Gen;
    @SerializedName("DOB")
    @Expose
    public String DOB;
    @SerializedName("DateOfBirth")
    @Expose
    public String DateOfBirth;
    @SerializedName("isYOB")
    @Expose
    public Boolean isYOB;
    @SerializedName("isPWD")
    @Expose
    public Boolean isPWD;
    @SerializedName("FatherLName")
    @Expose
    public String FatherLName;
    @SerializedName("FatherFName")
    @Expose
    public String FatherFName;
    @SerializedName("MotherLName")
    @Expose
    public String MotherLName;
    @SerializedName("MotherFName")
    @Expose
    public String MotherFName;
    @SerializedName("ProvOfBirthFK")
    @Expose
    public Integer ProvOfBirthFK;
    @SerializedName("ProvinceOfBirth")
    @Expose
    public String ProvinceOfBirth;
    @SerializedName("OpenElectorate")
    @Expose
    public String OpenElectorate;
    @SerializedName("PlaceOfBirth")
    @Expose
    public String PlaceOfBirth;
    @SerializedName("OccupationFK")
    @Expose
    public Integer OccupationFK;
    @SerializedName("Occupation")
    @Expose
    public String Occupation;
    @SerializedName("Religion")
    @Expose
    public String Religion;
    @SerializedName("ClanFK")
    @Expose
    public Integer ClanFK;
    @SerializedName("Clan")
    @Expose
    public String Clan;
    @SerializedName("SubClan")
    @Expose
    public String SubClan;
    @SerializedName("Address")
    @Expose
    public String Address;
    @SerializedName("NatID")
    @Expose
    public String NatID;
    @SerializedName("NatIDRecordNo")
    @Expose
    public String NatIDRecordNo;
    @SerializedName("Status")
    @Expose
    public String Status;
    @SerializedName("ProvinceName")
    @Expose
    public String ProvinceName;
    @SerializedName("DistrictName")
    @Expose
    public String DistrictName;
    @SerializedName("LLGName")
    @Expose
    public String LLGName;
    @SerializedName("WardName")
    @Expose
    public String WardName;
    @SerializedName("LocationName")
    @Expose
    public String LocationName;
    @SerializedName("HouseFK")
    @Expose
    public Integer HouseFK;
    @SerializedName("HouseSortOrder")
    @Expose
    public Integer HouseSortOrder;
    @SerializedName("RestHouse")
    @Expose
    public String RestHouse;
    @SerializedName("SectionNo")
    @Expose
    public Integer SectionNo;
    @SerializedName("LotNo")
    @Expose
    public Integer LotNo;
    @SerializedName("StreetName")
    @Expose
    public String StreetName;
    @SerializedName("SuburbTownSettlement")
    @Expose
    public String SuburbTownSettlement;
    @SerializedName("FamilyFK")
    @Expose
    public Integer FamilyFK;
    @SerializedName("FamilySortOrder")
    @Expose
    public Integer FamilySortOrder;
    @SerializedName("FamilyNumber")
    @Expose
    public Integer FamilyNumber;
    @SerializedName("FamilyName")
    @Expose
    public String FamilyName;
    @SerializedName("dpo")
    @Expose
    public String dpo;
    @SerializedName("Lat")
    @Expose
    public Double Lat;
    @SerializedName("Long")
    @Expose
    public Double Long;
    @SerializedName("posted")
    @Expose
    public String posted;
    @SerializedName("Comment")
    @Expose
    public String Comment;
    @SerializedName("ElectorProfileImage")
    @Expose
    public String ElectorProfileImage;
    @SerializedName("ElectorProfileImageUrl")
    @Expose
    public String ElectorProfileImageUrl;
    @SerializedName("ElectorFormImage")
    @Expose
    public String ElectorFormImage;
    @SerializedName("ElectorFormImageUrl")
    @Expose
    public String ElectorFormImageUrl;
    @SerializedName("ElectorProfileImageFile")
    @Expose
    public String ElectorProfileImageFile;
    @SerializedName("ElectorFormImageFile")
    @Expose
    public String ElectorFormImageFile;
    @SerializedName("EnrolmentFormNumber")
    @Expose
    public String EnrolmentFormNumber;
    @SerializedName("CreatedBy")
    @Expose
    public Integer CreatedBy;
    @SerializedName("TempElectorId")
    @Expose
    public Integer TempElectorId;
    @SerializedName("DuplicateId")
    @Expose
    public Integer DuplicateId;
    @SerializedName("AuditDescription")
    @Expose
    public String AuditDescription;
    @SerializedName("HasTransferInForm11")
    @Expose
    public Boolean HasTransferInForm11;
    @SerializedName("TransferInRegionId")
    @Expose
    public Integer TransferInRegionId;
    @SerializedName("TransferInProvinceId")
    @Expose
    public Integer TransferInProvinceId;
    @SerializedName("TransferInDistrictId")
    @Expose
    public Integer TransferInDistrictId;
    @SerializedName("TransferInLLGId")
    @Expose
    public Integer TransferInLLGId;
    @SerializedName("TransferInWardId")
    @Expose
    public Integer TransferInWardId;
    @SerializedName("TransferInLocationId")
    @Expose
    public Integer TransferInLocationId;
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
    @SerializedName("IsRejectedByRD")
    @Expose
    public Integer IsRejectedByRD;
    @SerializedName("CreatedDate")
    @Expose
    public String CreatedDate;
    @SerializedName("ErrorMessage")
    @Expose
    public String ErrorMessage;
    @SerializedName("ARLat")
    @Expose
    public Double ARLat;
    @SerializedName("ARLong")
    @Expose
    public Double ARLong;
    @SerializedName("RejectedReason")
    @Expose
    private String RejectedReason;
    @SerializedName("IsApproveOrReject")
    @Expose
    private String IsApproveOrReject;
    @SerializedName("ModifiedDate")
    @Expose
    private String ModifiedDate;
    @SerializedName("ModifiedByName")
    @Expose
    private String ModifiedByName;
    @SerializedName("IsRejected")
    @Expose
    private Boolean IsRejected;
    @SerializedName("DuplicateElectorId")
    @Expose
    private Integer DuplicateElectorId;
    @SerializedName("DuplicateLName")
    @Expose
    private String DuplicateLName;
    @SerializedName("DuplicateFName")
    @Expose
    private String DuplicateFName;
    @SerializedName("DuplicateVoterName")
    @Expose
    private String DuplicateVoterName;
    @SerializedName("DuplicateGender")
    @Expose
    private String DuplicateGender;
    @SerializedName("DuplicateGen")
    @Expose
    private String DuplicateGen;
    @SerializedName("DuplicateDOB")
    @Expose
    private String DuplicateDOB;
    @SerializedName("DuplicateDateOfBirth")
    @Expose
    private String DuplicateDateOfBirth;
    @SerializedName("DuplicateOccupation")
    @Expose
    private String DuplicateOccupation;
    @SerializedName("DuplicateProvinceName")
    @Expose
    private String DuplicateProvinceName;
    @SerializedName("DuplicateDistrictName")
    @Expose
    private String DuplicateDistrictName;
    @SerializedName("DuplicateLLGName")
    @Expose
    private String DuplicateLLGName;
    @SerializedName("DuplicateWardName")
    @Expose
    private String DuplicateWardName;
    @SerializedName("DuplicateLocationName")
    @Expose
    private String DuplicateLocationName;
    @SerializedName("RecordType")
    @Expose
    private String RecordType;
    @SerializedName("DuplicateRecordType")
    @Expose
    private String DuplicateRecordType;
    @SerializedName("ElectorSignatureImage")
    @Expose
    private String ElectorSignatureImage;
    @SerializedName("ElectorFingerPrintImage")
    @Expose
    private String ElectorFingerPrintImage;
    @SerializedName("ElectorProfileImageBinary")
    @Expose
    private String ElectorProfileImageBinary;
    @SerializedName("ElectorSignatureImageBinary")
    @Expose
    private String ElectorSignatureImageBinary;
}