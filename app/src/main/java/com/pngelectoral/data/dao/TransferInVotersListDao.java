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
public interface TransferInVotersListDao {

    @Query("SELECT * FROM voterslist")
    List<VotersList> getAll();

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isDataUploaded = 0 And status = :transferredIn")
    List<VotersList> getTransferInVotersData(String transferredIn);

    @Query("SELECT * FROM voterslist WHERE isSyncToServer = 0 And isImageUploaded = 0 And status = :transferredIn")
    List<VotersList> getTransferInVotersImages(String transferredIn);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VotersList transferinvoter);

    @Delete
    void delete(VotersList transferinvoter);

    @Update
    void update(VotersList transferinvoter);

    @Query("UPDATE voterslist SET isDataUploaded = 1 WHERE tempElectorId = :localVoterId")
    void updateDataSyncStatus(Integer localVoterId);

    @Query("UPDATE voterslist SET isImageUploaded = 1 WHERE tempElectorId = :localVoterId")
    void updateImageSyncStatus(Integer localVoterId);

    @Query("SELECT * FROM voterslist WHERE enrolmentFormNumber = :enrolmentFormNumber")
    VotersList checkEnrolmentFormNumber(String enrolmentFormNumber);

    @Query("UPDATE voterslist SET isSyncToServer = 1  WHERE electorId = :localVoterId")
    void updateVoterSyncStatus(Long localVoterId);

    @Query("UPDATE voterslist SET errorMessage =:errorMessage  WHERE electorId = :localVoterId")
    void updateWithErrorMessage(Long localVoterId, String errorMessage);

    @Query("UPDATE voterslist SET syncStatus =1  WHERE electorId = :localVoterId")
    void updateVoterSyncStatusAfterAPI(Long localVoterId);

    @Query("UPDATE voterslist SET syncDateTime =:syncDateTime WHERE electorId = :localVoterId")
    void updateVoterSyncStatusWithUpdatedDateTime(Long localVoterId, String syncDateTime);

    @Query("DELETE  FROM voterslist")
    void deleteAll();

    @Query("UPDATE voterslist SET isSyncToServer = 0  WHERE electorId = :electorId")
    void updateVoterSyncStatusToUnSync(Long electorId);

    @Query("UPDATE voterslist SET syncStatus =0  WHERE electorId = :localVoterId")
    void updateVoterSyncStatusAfterAPIToUnSync(Long localVoterId);

    @Query("UPDATE voterslist SET syncDateTime =:syncDateTime WHERE electorId = :localVoterId")
    void updateVoterSyncStatusWithUpdatedDateTimeUnSync(Long localVoterId, String syncDateTime);
}
