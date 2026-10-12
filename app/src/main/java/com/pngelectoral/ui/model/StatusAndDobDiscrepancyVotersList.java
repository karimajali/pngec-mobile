package com.pngelectoral.ui.model;

import java.io.Serializable;
import androidx.room.ColumnInfo;

public class StatusAndDobDiscrepancyVotersList implements Serializable {

    @ColumnInfo(name = "rowNo")
    public Long rowNo;

    @ColumnInfo(name = "electorId")
    public Long electorId;

    @ColumnInfo(name = "lName")
    public String lName;

    @ColumnInfo(name = "fName")
    public String fName;

    @ColumnInfo(name = "dob")
    public String dob;

    @ColumnInfo(name = "dateOfBirth")
    public String dateOfBirth;

    @ColumnInfo(name = "locationName")
    public String locationName;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "status")
    public String status;

    @ColumnInfo(name = "reason")
    public String reason;

}