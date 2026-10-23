package com.pngelectoral.data.dao;

import com.pngelectoral.ui.model.VotersList;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface VotersListDao {

    @Query("SELECT * FROM voterslist ORDER BY posted DESC")
    List<VotersList> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VotersList voter);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<VotersList> votersList);

    @Delete
    void delete(VotersList voter);

    @Update
    void update(VotersList voter);

    @Query("UPDATE voterslist SET isSyncToServer = 1, isDataUploaded = 1,status = 'OLD' WHERE electorId = :updateElectorID")
    void revertVoterStatus(Long updateElectorID);

    @Query("UPDATE voterslist SET electorId = :newElectorId, isSyncToServer = 1, isDataUploaded = 1, syncStatus = 1, status = 'OLD', syncDateTime = :syncDateTime WHERE (uploadId IS NOT NULL AND uploadId != '' AND uploadId = :uploadId) OR (tempElectorId IS NOT NULL AND tempElectorId = :tempElectorId AND tempElectorId > 0) OR (electorId IS NOT NULL AND electorId = :newElectorId)")
    void markVoterAsSynced(Long newElectorId, String uploadId, Integer tempElectorId, String syncDateTime);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0")
    List<VotersList> getUpdatedDataOnlyVotersList();

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0")
    List<VotersList> getUpdatedVotersListToSync();

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName")
    List<VotersList> getDPOWiseUpdatedVotersListToSync(String dpoName);

//    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName And wardId=:wardId")
//    List<VotersList> getDPOWARDWiseUpdatedVotersListToSync(String dpoName,String wardId);
//
//    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName And wardId=:wardId And locationId=:locationId")
//    List<VotersList> getDPOLOCATIONWiseUpdatedVotersListToSync(String dpoName,String wardId,String locationId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And wardId=:wardId")
    List<VotersList> getDPOWARDWiseUpdatedVotersListToSync(String wardId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And wardId=:wardId And locationId=:locationId")
    List<VotersList> getDPOLOCATIONWiseUpdatedVotersListToSync(String wardId, String locationId);

    @Query("SELECT * FROM voterslist WHERE isImageUploaded = 0 AND ((electorProfileImage IS NOT NULL AND electorProfileImage != '') OR (electorFormImage IS NOT NULL AND electorFormImage != ''))")
    List<VotersList> getUpdatedImageOnlyVotersList();

    @Query("UPDATE voterslist SET isImageUploaded = 1 WHERE (electorId IS NOT NULL AND electorId = :electorId) OR (uploadId IS NOT NULL AND uploadId != '' AND uploadId = :uploadId)")
    void markImageAsUploaded(Long electorId, String uploadId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0")
    List<VotersList> getUpdatedVotersList();

//    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And dpo =:dpoName AND status IN (:statusFilterList)")
//    List<VotersList> getNewAndTransferInUpdatedVotersList(List<String> statusFilterList,String dpoName);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 AND status IN (:statusFilterList)")
    List<VotersList> getNewAndTransferInUpdatedVotersList(List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 ")
    List<VotersList> getSyncFailedVotersList();

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 AND status IN (:statusFilterList)")
    List<VotersList> getSyncFailedVotersListWithStatusFilter(List<String> statusFilterList);

    @Query("UPDATE voterslist SET isSyncToServer = 1  WHERE electorId = :localVoterId")
    void updateVoterSyncStatus(Long localVoterId);

    @Query("UPDATE voterslist SET isSyncToServer = 1  WHERE tempElectorId = :localVoterId")
    void updateNewVoterSyncStatus(Integer localVoterId);

    @Query("UPDATE voterslist SET errorMessage =:errorMessage  WHERE electorId = :localVoterId")
    void updateWithErrorMessage(Long localVoterId, String errorMessage);

    @Query("UPDATE voterslist SET errorMessage =:errorMessage  WHERE tempElectorId = :localVoterId")
    void updateWithErrorMessageNewVoter(Integer localVoterId, String errorMessage);

    @Query("UPDATE voterslist SET syncStatus =1  WHERE electorId = :localVoterId")
    void updateVoterSyncStatusAfterAPI(Long localVoterId);

    @Query("UPDATE voterslist SET syncStatus =1  WHERE tempElectorId = :localVoterId")
    void updateNewVoterSyncStatusAfterAPI(Integer localVoterId);

    @Query("UPDATE voterslist SET syncDateTime =:syncDateTime WHERE electorId = :localVoterId")
    void updateVoterSyncStatusWithUpdatedDateTime(Long localVoterId, String syncDateTime);

    @Query("UPDATE voterslist SET syncDateTime =:syncDateTime WHERE tempElectorId = :localVoterId")
    void updateNewVoterSyncStatusWithUpdatedDateTime(Integer localVoterId, String syncDateTime);

    @Query("UPDATE voterslist SET isDataUploaded = 1 WHERE tempElectorId = :localVoterId")
    void updateDataSyncStatus(Integer localVoterId);

    @Query("UPDATE voterslist SET isImageUploaded = 1 WHERE tempElectorId = :localVoterId")
    void updateImageSyncStatus(Integer localVoterId);

    @Query("DELETE  FROM voterslist")
    void deleteAll();

    @Query("SELECT * FROM voterslist WHERE enrolmentFormNumber = :enrolmentFormNumber")
    VotersList checkEnrolmentFormNumber(String enrolmentFormNumber);

    @Query("SELECT * FROM voterslist WHERE electorId = :electorId")
    VotersList checkRejectVoterUpdated(Long electorId);

    @Query("SELECT * FROM voterslist WHERE electorId = :electorId")
    VotersList checkVoterDownloaded(Long electorId);

    @Query("SELECT * FROM voterslist WHERE tempElectorId = :tempElectorId")
    VotersList checkVoterDownloadedWhileUpdate(Integer tempElectorId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND status IN (:statusFilterList) ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                   int filterLLGId, int filterWardId, int filterLocationId,
                                                   List<String> statusFilterList);
    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND status IN (:statusFilterList) AND clanId = :clanId ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollFilteredVotersListClan(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                   int filterLLGId, int filterWardId, int filterLocationId,
                                                   List<String> statusFilterList,int clanId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND status IN (:statusFilterList) AND clanId is null ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollFilteredVotersListClanNull(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                       int filterLLGId, int filterWardId, int filterLocationId,
                                                       List<String> statusFilterList);


    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND status IN (:statusFilterList) AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                             int filterLLGId, int filterWardId, int filterLocationId,
                                                             List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND status IN (:statusFilterList) AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListLike(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                             int filterLLGId, int filterWardId, int filterLocationId,
                                                             List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId);


    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND status IN (:statusFilterList) AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId = :clanId ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListClan(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                             int filterLLGId, int filterWardId, int filterLocationId,
                                                             List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId,int clanId);


    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND status IN (:statusFilterList) AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId = :clanId ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListClanLike(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                                 int filterLLGId, int filterWardId, int filterLocationId,
                                                                 List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId,int clanId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND status IN (:statusFilterList) AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId is null ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListClanNull(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                                 int filterLLGId, int filterWardId, int filterLocationId,
                                                                 List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND status IN (:statusFilterList) AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId is null ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListClanNullLike(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                                     int filterLLGId, int filterWardId, int filterLocationId,
                                                                     List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId);


    /**
     * Filter method for PNGEC geo structure
     */
    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND status IN (:statusFilterList) ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollFilteredVotersListForPNGEC(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                           int filterLLGId, int filterWardId, int filterLocationId,
                                                           List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND status IN (:statusFilterList) AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListForPNGEC(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                                     int filterLLGId, int filterWardId, int filterLocationId,
                                                                     List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId);

    /**
     * Filter method for MKA role with status filter list including location.
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId AND status IN (:statusFilterList) ORDER BY LOWER(lName) ASC")
    List<VotersList> getFilteredVotersListForMKAWithLocation(int filterDistrictId, int filterLLGId, int filterWardId,
                                                             int filterLocationId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId AND status IN (:statusFilterList) AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseFilteredVotersListForMKAWithLocation(int filterDistrictId, int filterLLGId, int filterWardId,
                                                                       int filterLocationId, List<String> statusFilterList,
                                                                       String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND status IN (:statusFilterList) ORDER BY LOWER(voterName) ASC")
    List<VotersList> getWardRollDistrictFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND status IN (:statusFilterList) ORDER BY LOWER(voterName) ASC")
    List<VotersList> getWardRollLLGFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND status IN (:statusFilterList) ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollWardFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND status IN (:statusFilterList) AND clanId = :clanId ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollWardFilteredVotersListClan(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList,int clanId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND status IN (:statusFilterList) AND clanId is null ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollWardFilteredVotersListClanNull(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList);


    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND status IN (:statusFilterList) AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId);


    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND status IN (:statusFilterList) AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListLike(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND status IN (:statusFilterList) AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId = :clanId ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListClan(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId,int clanId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND status IN (:statusFilterList) AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId = :clanId ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListClanLike(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId,int clanId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND status IN (:statusFilterList) AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId is null ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListClanNull(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND status IN (:statusFilterList) AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId is null ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListClanNullLike(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId);


    /**
     * Filter method for PNGEC geo structure
     */
    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND status IN (:statusFilterList) ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollWardFilteredVotersListForPNGEC(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND status IN (:statusFilterList) AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListForPNGEC(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId);

    /**
     * Filter method for MKA role with status filter list.
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND status IN (:statusFilterList) ORDER BY LOWER(lName) ASC")
    List<VotersList> getFilteredVotersListForMKA(int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND status IN (:statusFilterList) AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseFilteredVotersListForMKA(int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusFilterList, String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                   int filterLLGId, int filterWardId, int filterLocationId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND clanId = :clanId ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollFilteredVotersListClan(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                   int filterLLGId, int filterWardId, int filterLocationId,int clanId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND clanId is null ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollFilteredVotersListClanNull(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                       int filterLLGId, int filterWardId, int filterLocationId);


    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                             int filterLLGId, int filterWardId, int filterLocationId,
                                                             String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListLike(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                             int filterLLGId, int filterWardId, int filterLocationId,
                                                             String gender, String searchFName, String searchLName, String searchElectorId);


    @Query("SELECT * FROM voterslist WHERE  regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId = :clanId  ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListClan(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                             int filterLLGId, int filterWardId, int filterLocationId,
                                                             String gender, String searchFName, String searchLName, String searchElectorId,int clanId);

    @Query("SELECT * FROM voterslist WHERE  regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId = :clanId  ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListClanLike(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                                 int filterLLGId, int filterWardId, int filterLocationId,
                                                                 String gender, String searchFName, String searchLName, String searchElectorId,int clanId);


    @Query("SELECT * FROM voterslist WHERE  regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId is null  ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListClanNull(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                                 int filterLLGId, int filterWardId, int filterLocationId,
                                                                 String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE  regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId is null  ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListClanNullLike(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                                     int filterLLGId, int filterWardId, int filterLocationId,
                                                                     String gender, String searchFName, String searchLName, String searchElectorId);

    /**
     * Filter method for PNGEC geo structure
     */
    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollFilteredVotersListForPNGEC(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                           int filterLLGId, int filterWardId, int filterLocationId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollFilteredVotersListForPNGEC(int filterRegionId, int filterProvinceId, int filterDistrictId,
                                                                     int filterLLGId, int filterWardId, int filterLocationId,
                                                                     String gender, String searchFName, String searchLName, String searchElectorId);

    /**
     * Filter method for MKA role with location.
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkaLlgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId ORDER BY LOWER(lName) ASC")
    List<VotersList> getFilteredVotersListForMKAWithLocation(int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId);

    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkaLlgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseFilteredVotersListForMKAWithLocation(int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId ORDER BY LOWER(voterName) ASC")
    List<VotersList> getWardRollDistrictFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId ORDER BY LOWER(voterName) ASC")
    List<VotersList> getWardRollLLGFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollWardFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND ( NULLIF(gender, '') is null  OR LOWER(gender) not in('f','m')) OR  clanId is null  ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollWardFilteredVotersListIsClanGenderNull(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId);


    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND mkaWardId = :filterWardId AND ( NULLIF(gender, '') is null  OR LOWER(gender) not in('f','m')) OR  clanId is null  ORDER BY LOWER(lName) ASC")
    List<VotersList> getMkaWardRollWardFilteredVotersListIsClanGenderNull(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId);


    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND( NULLIF(gender, '') is null  OR LOWER(gender) not in('f','m')) OR  clanId is null  ORDER BY LOWER(lName) ASC")
    List<VotersList> getLocationRollLocationFilteredVotersListIsClanGenderNull(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND clanId = :clanId ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollWardFilteredVotersListClan(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId,int clanId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND clanId is null ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollWardFilteredVotersListClanNull(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId);


    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%'  ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%'  ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListLike(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String gender, String searchFName, String searchLName, String searchElectorId);


    @Query("SELECT * FROM voterslist WHERE  regionId = :filterRegionId AND regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId = :clanId  ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListClan(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String gender, String searchFName, String searchLName, String searchElectorId,int clanId);

    @Query("SELECT * FROM voterslist WHERE  regionId = :filterRegionId AND regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId = :clanId  ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListClanLike(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String gender, String searchFName, String searchLName, String searchElectorId,int clanId);

    @Query("SELECT * FROM voterslist WHERE  regionId = :filterRegionId AND regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId is null  ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListClanNull(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE  regionId = :filterRegionId AND regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND (NULLIF(gender, '') is null OR LOWER(gender) not in('f','m') OR gender = :gender ) AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' AND clanId is null  ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListClanNullLike(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String gender, String searchFName, String searchLName, String searchElectorId);

    /**
     * Filter method for PNGEC geo structure
     */
    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId ORDER BY LOWER(lName) ASC")
    List<VotersList> getWardRollWardFilteredVotersListForPNGEC(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseWardRollWardFilteredVotersListForPNGEC(int filterRegionId, int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String gender, String searchFName, String searchLName, String searchElectorId);

    /**
     * Filter method for MKA role.
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkaLlgId = :filterLLGId AND mkaWardId = :filterWardId ORDER BY LOWER(lName) ASC")
    List<VotersList> getFilteredVotersListForMKA(int filterDistrictId, int filterLLGId, int filterWardId);

    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkaLlgId = :filterLLGId AND mkaWardId = :filterWardId AND gender LIKE '%' || :gender || '%' AND fName LIKE '%' || :searchFName || '%' AND lName LIKE '%' || :searchLName || '%' AND electorId LIKE '%' || :searchElectorId || '%' ORDER BY LOWER(lName) ASC")
    List<VotersList> getSearchWiseFilteredVotersListForMKA(int filterDistrictId, int filterLLGId, int filterWardId, String gender, String searchFName, String searchLName, String searchElectorId);

    @Query("SELECT * FROM voterslist WHERE  wardId = :filterWardId ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDuplicateWardFilteredVotersList(int filterWardId);

    /*For MKA*/
    @Query("SELECT * FROM voterslist WHERE  mkaWardId = :filterWardId ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDuplicateWardFilteredVotersListForMKA(int filterWardId);

    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersList(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String dpoName);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListForMKA(int filterDistrictId, int filterLLGId, int filterWardId, String dpoName);

    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0  ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersList(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0  ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListForMKA(int filterDistrictId, int filterLLGId, int filterWardId);

    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListBetweenDates(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String startDate, String endDate, String dpoName);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListBetweenDatesForMKA(int filterDistrictId, int filterLLGId, int filterWardId, String startDate, String endDate, String dpoName);

    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0  And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListBetweenDates(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String startDate, String endDate);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0  And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListBetweenDatesForMKA(int filterDistrictId, int filterLLGId, int filterWardId, String startDate, String endDate);

    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersList(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String dpoName);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListForMKA(int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String dpoName);

    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0  ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersList(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0  ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListForMKA(int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId);

    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListBetweenDates(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String startDate, String endDate, String dpoName);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListBetweenDatesForMKA(int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String startDate, String endDate, String dpoName);

    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0  And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListBetweenDates(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String startDate, String endDate);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0  And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getDeviceOperatorReportVotersListBetweenDatesForMKA(int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String startDate, String endDate);

    @Query("delete from voterslist WHERE tempElectorId IN (:tempElectorIdList)")
    void deleteNewAndTransferInVoters(List<Integer> tempElectorIdList);

    @Query("delete from voterslist WHERE tempElectorId = :tempElectorId")
    void deleteNewAndTransferInVoter(int tempElectorId);

//    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName AND  status NOT IN (:statusList) ORDER BY LOWER(voterName) ASC")
//    List<VotersList> getFieldTallySummaryReportVotersList(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusList,String dpoName);
//
//    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName AND status NOT IN (:statusList) And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
//    List<VotersList> getFieldTallySummaryReportVotersListBetweenDates(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String startDate, String endDate, List<String> statusList,String dpoName);
//
//    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName AND status NOT IN (:statusList) ORDER BY LOWER(voterName) ASC")
//    List<VotersList> getFieldTallySummaryReportVotersList(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, List<String> statusList,String dpoName);
//
//    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0 And dpo =:dpoName AND status NOT IN (:statusList) And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
//    List<VotersList> getFieldTallySummaryReportVotersListBetweenDates(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String startDate, String endDate, List<String> statusList,String dpoName);


    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0 AND  status NOT IN (:statusList) ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersList(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusList);

    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId  ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListPhoto(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId);


    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0 AND  status NOT IN (:statusList) ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListForMKA(int filterDistrictId, int filterLLGId, int filterWardId, List<String> statusList);

    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId  ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListForMKAPhoto(int filterDistrictId, int filterLLGId, int filterWardId);


    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0 AND status NOT IN (:statusList) And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListBetweenDates(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String startDate, String endDate, List<String> statusList);

    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId  And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListBetweenDatesPhoto(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, String startDate, String endDate);



    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND isSyncToServer = 0 And isDataUploaded = 0 AND status NOT IN (:statusList) And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListBetweenDatesForMKA(int filterDistrictId, int filterLLGId, int filterWardId, String startDate, String endDate, List<String> statusList);

    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId  And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListBetweenDatesForMKAPhoto(int filterDistrictId, int filterLLGId, int filterWardId, String startDate, String endDate);


    @Query("SELECT * FROM voterslist WHERE province" +
            "Id = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0 AND status NOT IN (:statusList) ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersList(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, List<String> statusList);


    @Query("SELECT * FROM voterslist WHERE province" +
            "Id = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId  ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListPhoto(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId);


    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0 AND status NOT IN (:statusList) ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListForMKA(int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, List<String> statusList);

    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId  ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListForMKAPhoto(int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId);


    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0 ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersPhotoList(int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId);


    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0 AND status NOT IN (:statusList) And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListBetweenDates(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String startDate, String endDate, List<String> statusList);

    @Query("SELECT * FROM voterslist WHERE provinceId = :filterProvinceId AND districtId = :filterDistrictId AND llgId = :filterLLGId AND wardId = :filterWardId AND locationId = :filterLocationId  And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListBetweenDatesPhoto(int filterProvinceId, int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String startDate, String endDate);


    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId AND isSyncToServer = 0 And isDataUploaded = 0 AND status NOT IN (:statusList) And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListBetweenDatesForMKA(int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String startDate, String endDate, List<String> statusList);

    @Query("SELECT * FROM voterslist WHERE mkaDistrictId = :filterDistrictId AND mkallgId = :filterLLGId AND mkaWardId = :filterWardId AND mkaLocationId = :filterLocationId And updatedDate BETWEEN :startDate AND :endDate ORDER BY LOWER(voterName) ASC")
    List<VotersList> getFieldTallySummaryReportVotersListBetweenDatesForMKAPhoto(int filterDistrictId, int filterLLGId, int filterWardId, int filterLocationId, String startDate, String endDate);


    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId")
    List<VotersList> checkDistrictWiseUpdatedVotersListToSync(String districtId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId And llgId=:llgId")
    List<VotersList> checkLLGWiseUpdatedVotersListToSync(String districtId, String llgId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId And llgId=:llgId And wardId=:wardId")
    List<VotersList> checkWardWiseUpdatedVotersListToSync(String districtId, String llgId, String wardId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId And llgId=:llgId And wardId=:wardId And locationId=:locationId")
    List<VotersList> checkLocationWiseUpdatedVotersListToSync(String districtId, String llgId, String wardId, String locationId);

    @Query("SELECT * FROM voterslist WHERE regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId ORDER BY LOWER(voterName) ASC")
    List<VotersList> getAllVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId);

    @Query("SELECT * FROM voterslist WHERE  regionId = :filterRegionId AND provinceId = :filterProvinceId AND districtId = :filterDistrictId AND gender =:emptyGender ORDER BY LOWER(voterName) ASC")
    List<VotersList> getGenderLessVotersList(int filterRegionId, int filterProvinceId, int filterDistrictId, String emptyGender);

    @Query("SELECT * FROM voterslist WHERE  districtId = :filterDistrictId AND gender NOT IN (:genderFilterList) ORDER BY LOWER(voterName) ASC")
    List<VotersList> getGenderLessVotersList(int filterDistrictId, List<String> genderFilterList);

    @Query("SELECT * FROM voterslist WHERE  districtId = :filterDistrictId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDistrictVotersList(int filterDistrictId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE  districtId = :filterDistrictId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDistrictVotersListForMKA(int filterDistrictId);

    @Query("SELECT * FROM voterslist WHERE  llgId = :filterLLGId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getLLGVotersList(int filterLLGId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE  llgId = :filterLLGId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getLLGVotersListForMKA(int filterLLGId);

    @Query("SELECT * FROM voterslist WHERE  wardId = :filterWardId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getWardVotersList(int filterWardId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE  wardId = :filterWardId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getWardVotersListForMKA(int filterWardId);

    @Query("SELECT * FROM voterslist WHERE  locationId = :filterLocationId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getLocationVotersList(int filterLocationId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE  mkaLocationId = :filterLocationId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getLocationVotersListForMKA(int filterLocationId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPODistrictWiseUpdatedVotersListToSync(String districtId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And mkaDistrictId=:districtId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPODistrictWiseUpdatedVotersListToSyncForMKA(String districtId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And districtId=:districtId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPODistrictWiseUpdatedVotersListToSyncWithImage(String districtId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And mkaDistrictId=:districtId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPODistrictWiseUpdatedVotersListToSyncWithImageForMKA(String districtId);

    @Query("SELECT * FROM voterslist WHERE isImageUploaded = 0 And districtId=:districtId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getAllForm11AndVoterImages(String districtId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPODistrictWiseUpdatedVotersListToSyncWithStatusFilter(String districtId, List<String> statusFilterList);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And mkaDistrictId=:districtId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPODistrictWiseUpdatedVotersListToSyncWithStatusFilterForMKA(String districtId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And districtId=:districtId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPODistrictWiseUpdatedVotersListToSyncWithStatusFilterAndImage(String districtId, List<String> statusFilterList);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And mkaDistrictId=:districtId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPODistrictWiseUpdatedVotersListToSyncWithStatusFilterAndImageForMKA(String districtId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId And llgId=:llgId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLLGWiseUpdatedVotersListToSync(String districtId, String llgId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And mkaDistrictId=:districtId And mkaLlgId=:llgId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLLGWiseUpdatedVotersListToSyncForMKA(String districtId, String llgId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And districtId=:districtId And llgId=:llgId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLLGWiseUpdatedVotersListToSyncWithImage(String districtId, String llgId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And mkaDistrictId=:districtId And mkaLlgId=:llgId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLLGWiseUpdatedVotersListToSyncWithImageForMKA(String districtId, String llgId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId And llgId=:llgId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLLGWiseUpdatedVotersListToSyncWithStatusFilter(String districtId, String llgId, List<String> statusFilterList);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And mkaDistrictId=:districtId And mkaLlgId=:llgId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLLGWiseUpdatedVotersListToSyncWithStatusFilterForMKA(String districtId, String llgId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And districtId=:districtId And llgId=:llgId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLLGWiseUpdatedVotersListToSyncWithStatusFilterAndImage(String districtId, String llgId, List<String> statusFilterList);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And mkaDistrictId=:districtId And mkaLlgId=:llgId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLLGWiseUpdatedVotersListToSyncWithStatusFilterAndImageForMKA(String districtId, String llgId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId And llgId=:llgId And wardId=:wardId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOWARDWiseUpdatedVotersListToSync(String districtId, String llgId, String wardId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And mkaDistrictId=:districtId And mkaLlgId=:llgId And mkaWardId=:wardId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOWARDWiseUpdatedVotersListToSyncForMKA(String districtId, String llgId, String wardId);

    /**@Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And districtId=:districtId And llgId=:llgId And wardId=:wardId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")*/
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0  And districtId=:districtId And llgId=:llgId And wardId=:wardId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOWARDWiseUpdatedVotersListToSyncWithImage(String districtId, String llgId, String wardId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0  And mkaDistrictId=:districtId And mkaLlgId=:llgId And mkaWardId=:wardId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOWARDWiseUpdatedVotersListToSyncWithImageForMKA(String districtId, String llgId, String wardId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId And llgId=:llgId And wardId=:wardId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOWARDWiseUpdatedVotersListToSyncWithStatusFilter(String districtId, String llgId, String wardId, List<String> statusFilterList);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And mkaDistrictId=:districtId And mkaLlgId=:llgId And mkaWardId=:wardId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOWARDWiseUpdatedVotersListToSyncWithStatusFilterForMKA(String districtId, String llgId, String wardId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And districtId=:districtId And llgId=:llgId And wardId=:wardId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOWARDWiseUpdatedVotersListToSyncWithStatusFilterAndImage(String districtId, String llgId, String wardId, List<String> statusFilterList);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And mkaDistrictId=:districtId And mkaLlgId=:llgId And mkaWardId=:wardId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOWARDWiseUpdatedVotersListToSyncWithStatusFilterAndImageForMKA(String districtId, String llgId, String wardId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId And llgId=:llgId And wardId=:wardId And locationId=:locationId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLOCATIONWiseUpdatedVotersListToSync(String districtId, String llgId, String wardId, String locationId);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And mkaDistrictId=:districtId And mkaLlgId=:llgId And mkaWardId=:wardId And mkaLocationId=:locationId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLOCATIONWiseUpdatedVotersListToSyncForMKA(String districtId, String llgId, String wardId, String locationId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0  And districtId=:districtId And llgId=:llgId And wardId=:wardId And locationId=:locationId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLOCATIONWiseUpdatedVotersListToSyncWithImage(String districtId, String llgId, String wardId, String locationId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0  And districtId=:districtId And llgId=:llgId And wardId=:wardId And locationId=:locationId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLOCATIONWiseUpdatedVotersListToSyncWithImageSync(String districtId, String llgId, String wardId, String locationId);



    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0  And mkaDistrictId=:districtId And mkaLlgId=:llgId And mkaWardId=:wardId And locationId=:locationId ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLOCATIONWiseUpdatedVotersListToSyncWithImageForMKA(String districtId, String llgId, String wardId, String locationId);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And districtId=:districtId And llgId=:llgId And wardId=:wardId And locationId=:locationId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLOCATIONWiseUpdatedVotersListToSyncWithStatusFilter(String districtId, String llgId, String wardId, String locationId, List<String> statusFilterList);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And mkaDistrictId=:districtId And mkaLlgId=:llgId And mkaWardId=:wardId And mkaLocationId=:locationId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLOCATIONWiseUpdatedVotersListToSyncWithStatusFilterForMKA(String districtId, String llgId, String wardId, String locationId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And districtId=:districtId And llgId=:llgId And wardId=:wardId And locationId=:locationId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLOCATIONWiseUpdatedVotersListToSyncWithStatusFilterAndImage(String districtId, String llgId, String wardId, String locationId, List<String> statusFilterList);

    /**
     * For MKA
     */
    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And isImageUploaded = 0 And mkaDistrictId=:districtId And mkaLlgId=:llgId And mkaWardId=:wardId And mkaLocationId=:locationId AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLOCATIONWiseUpdatedVotersListToSyncWithStatusFilterAndImageForMKA(String districtId, String llgId, String wardId, String locationId, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 1 And syncStatus = 1 And districtId=:districtId AND status NOT IN (:statusList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPODistrictWiseSyncedVotersList(String districtId, List<String> statusList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 1 And syncStatus = 1 And districtId=:districtId AND status NOT IN (:statusList) AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPODistrictWiseSyncedVotersListWithFilter(String districtId, List<String> statusList, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 1 And syncStatus = 1 And districtId=:districtId And llgId=:llgId AND status NOT IN (:statusList)  ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLLGWiseSyncedVotersList(String districtId, String llgId, List<String> statusList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 1 And syncStatus = 1 And districtId=:districtId And llgId=:llgId AND status NOT IN (:statusList)  AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLLGWiseSyncedVotersListWithFilter(String districtId, String llgId, List<String> statusList, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 1 And syncStatus = 1 And districtId=:districtId And llgId=:llgId And wardId=:wardId AND status NOT IN (:statusList)  ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOWARDWiseSyncedVotersList(String districtId, String llgId, String wardId, List<String> statusList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 1 And syncStatus = 1 And districtId=:districtId And llgId=:llgId And wardId=:wardId AND status NOT IN (:statusList)  AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOWARDWiseSyncedVotersListWithFilter(String districtId, String llgId, String wardId, List<String> statusList, List<String> statusFilterList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 1 And syncStatus = 1 And districtId=:districtId And llgId=:llgId And wardId=:wardId And locationId=:locationId AND status NOT IN (:statusList)  ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLOCATIONWiseSyncedVotersList(String districtId, String llgId, String wardId, String locationId, List<String> statusList);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 1 And syncStatus = 1 And districtId=:districtId And llgId=:llgId And wardId=:wardId And locationId=:locationId AND status NOT IN (:statusList)  AND status IN (:statusFilterList) ORDER BY LOWER(llgName) ASC,LOWER(wardName) ASC,LOWER(locationName) ASC,LOWER(lName) ASC,LOWER(fName) ASC")
    List<VotersList> getDPOLOCATIONWiseSyncedVotersListWithFilter(String districtId, String llgId, String wardId, String locationId, List<String> statusList, List<String> statusFilterList);

    @Query("UPDATE voterslist SET isSyncToServer = 0  WHERE tempElectorId = :localVoterId")
    void updateNewVoterSyncStatusToUnSync(Integer localVoterId);

    @Query("UPDATE voterslist SET isDataUploaded = 0  WHERE tempElectorId = :localVoterId")
    void updateNewVoterSyncStatusToUnSyncDataUpload(Integer localVoterId);

    @Query("UPDATE voterslist SET isImageUploaded = 0  WHERE tempElectorId = :localVoterId")
    void updateNewVoterSyncStatusToUnSyncImageUpload(Integer localVoterId);

    @Query("UPDATE voterslist SET syncStatus =0  WHERE tempElectorId = :localVoterId")
    void updateNewVoterSyncStatusAfterAPIToUnSync(Integer localVoterId);

    @Query("UPDATE voterslist SET syncDateTime =:syncDateTime WHERE tempElectorId = :localVoterId")
    void updateNewVoterSyncStatusWithUpdatedDateTimeUnSync(Integer localVoterId, String syncDateTime);

    @Query("UPDATE voterslist SET isSyncToServer = 0  WHERE electorId = :localVoterId")
    void updateVoterSyncStatusToUnSync(Long localVoterId);

    @Query("UPDATE voterslist SET isDataUploaded =0  WHERE electorId = :localVoterId")
    void updateVoterSyncStatusToUnSyncDataUpload(Long localVoterId);

    @Query("UPDATE voterslist SET isImageUploaded =0  WHERE electorId = :localVoterId")
    void updateVoterSyncStatusToUnSyncImageUpload(Long localVoterId);

    @Query("UPDATE voterslist SET syncStatus =0  WHERE electorId = :localVoterId")
    void updateVoterSyncStatusAfterAPIToUnSync(Long localVoterId);

    @Query("UPDATE voterslist SET syncDateTime =:syncDateTime WHERE electorId = :localVoterId")
    void updateVoterSyncStatusWithUpdatedDateTimeUnSync(Long localVoterId, String syncDateTime);

    @Query("UPDATE voterslist SET isSyncToServer = 1  WHERE electorId = :localVoterId")
    void updateOLDVoterSyncStatusToUnSync(Long localVoterId);

    @Query("UPDATE voterslist SET syncStatus =0  WHERE electorId = :localVoterId")
    void updateOLDVoterSyncStatusAfterAPIToUnSync(Long localVoterId);

    @Query("UPDATE voterslist SET syncDateTime =:syncDateTime WHERE electorId = :localVoterId")
    void updateOLDVoterSyncStatusWithUpdatedDateTimeUnSync(Long localVoterId, String syncDateTime);


}
