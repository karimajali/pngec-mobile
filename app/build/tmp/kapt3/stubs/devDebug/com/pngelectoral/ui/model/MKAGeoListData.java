package com.pngelectoral.ui.model;

import java.lang.System;

/**
 * Model class created for table in database
 */
@androidx.room.Entity(tableName = "mkageolist")
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\"\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\t\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\n\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\t\u001a\u0004\b\u000b\u0010\u0006\"\u0004\b\f\u0010\bR \u0010\r\u001a\u0004\u0018\u00010\u000e8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R \u0010\u0013\u001a\u0004\u0018\u00010\u000e8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0010\"\u0004\b\u0015\u0010\u0012\u00a8\u0006\u0016"}, d2 = {"Lcom/pngelectoral/ui/model/MKAGeoListData;", "Ljava/io/Serializable;", "()V", "actionBy", "", "getActionBy", "()Ljava/lang/Integer;", "setActionBy", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "districtId", "getDistrictId", "setDistrictId", "districtName", "", "getDistrictName", "()Ljava/lang/String;", "setDistrictName", "(Ljava/lang/String;)V", "llGs", "getLlGs", "setLlGs", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class MKAGeoListData implements java.io.Serializable {
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "actionBy")
    private java.lang.Integer actionBy = 0;
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "districtId")
    @androidx.room.PrimaryKey
    private java.lang.Integer districtId = 0;
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "districtName")
    private java.lang.String districtName = "";
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "llGs")
    private java.lang.String llGs = "";
    
    public MKAGeoListData() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getActionBy() {
        return null;
    }
    
    public final void setActionBy(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getDistrictId() {
        return null;
    }
    
    public final void setDistrictId(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDistrictName() {
        return null;
    }
    
    public final void setDistrictName(@org.jetbrains.annotations.Nullable
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getLlGs() {
        return null;
    }
    
    public final void setLlGs(@org.jetbrains.annotations.Nullable
    java.lang.String p0) {
    }
}