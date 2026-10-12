package com.pngelectoral.ui.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b-\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B}\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0002\u0010\u0010J\u0010\u0010/\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\u0010\u00100\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u0010\u00101\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u00102\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u00103\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u00104\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u00105\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u00106\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u00107\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u000b\u00108\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u0086\u0001\u00109\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00c6\u0001\u00a2\u0006\u0002\u0010:J\u0013\u0010;\u001a\u00020\u000f2\b\u0010<\u001a\u0004\u0018\u00010=H\u00d6\u0003J\t\u0010>\u001a\u00020\u0005H\u00d6\u0001J\t\u0010?\u001a\u00020\rH\u00d6\u0001R\"\u0010\t\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\"\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001a\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\"\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001e\u001a\u0004\b\u000e\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\b\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u001f\u0010\u0012\"\u0004\b \u0010\u0014R\"\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b!\u0010\u0012\"\u0004\b\"\u0010\u0014R\"\u0010\n\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b#\u0010\u0012\"\u0004\b$\u0010\u0014R\"\u0010\u000b\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b%\u0010\u0012\"\u0004\b&\u0010\u0014R \u0010\f\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010(\"\u0004\b)\u0010*R\"\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b+\u0010\u0012\"\u0004\b,\u0010\u0014R\"\u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b-\u0010\u0012\"\u0004\b.\u0010\u0014\u00a8\u0006@"}, d2 = {"Lcom/pngelectoral/ui/model/SyncedVoterStatusData;", "Ljava/io/Serializable;", "electorId", "", "tempElectorId", "", "locationId", "wardId", "llgId", "districtId", "provinceId", "regionId", "status", "", "isSynced", "", "(Ljava/lang/Long;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Boolean;)V", "getDistrictId", "()Ljava/lang/Integer;", "setDistrictId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getElectorId", "()Ljava/lang/Long;", "setElectorId", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "()Ljava/lang/Boolean;", "setSynced", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "getLlgId", "setLlgId", "getLocationId", "setLocationId", "getProvinceId", "setProvinceId", "getRegionId", "setRegionId", "getStatus", "()Ljava/lang/String;", "setStatus", "(Ljava/lang/String;)V", "getTempElectorId", "setTempElectorId", "getWardId", "setWardId", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Long;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Boolean;)Lcom/pngelectoral/ui/model/SyncedVoterStatusData;", "equals", "other", "", "hashCode", "toString", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class SyncedVoterStatusData implements java.io.Serializable {
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "electorId")
    private java.lang.Long electorId;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "tempElectorId")
    private java.lang.Integer tempElectorId;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "locationId")
    private java.lang.Integer locationId;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "wardId")
    private java.lang.Integer wardId;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "llgId")
    private java.lang.Integer llgId;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "districtId")
    private java.lang.Integer districtId;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "provinceId")
    private java.lang.Integer provinceId;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "regionId")
    private java.lang.Integer regionId;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "status")
    private java.lang.String status;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "isSynced")
    private java.lang.Boolean isSynced;
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.SyncedVoterStatusData copy(@org.jetbrains.annotations.Nullable
    java.lang.Long electorId, @org.jetbrains.annotations.Nullable
    java.lang.Integer tempElectorId, @org.jetbrains.annotations.Nullable
    java.lang.Integer locationId, @org.jetbrains.annotations.Nullable
    java.lang.Integer wardId, @org.jetbrains.annotations.Nullable
    java.lang.Integer llgId, @org.jetbrains.annotations.Nullable
    java.lang.Integer districtId, @org.jetbrains.annotations.Nullable
    java.lang.Integer provinceId, @org.jetbrains.annotations.Nullable
    java.lang.Integer regionId, @org.jetbrains.annotations.Nullable
    java.lang.String status, @org.jetbrains.annotations.Nullable
    java.lang.Boolean isSynced) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public java.lang.String toString() {
        return null;
    }
    
    public SyncedVoterStatusData() {
        super();
    }
    
    public SyncedVoterStatusData(@org.jetbrains.annotations.Nullable
    java.lang.Long electorId, @org.jetbrains.annotations.Nullable
    java.lang.Integer tempElectorId, @org.jetbrains.annotations.Nullable
    java.lang.Integer locationId, @org.jetbrains.annotations.Nullable
    java.lang.Integer wardId, @org.jetbrains.annotations.Nullable
    java.lang.Integer llgId, @org.jetbrains.annotations.Nullable
    java.lang.Integer districtId, @org.jetbrains.annotations.Nullable
    java.lang.Integer provinceId, @org.jetbrains.annotations.Nullable
    java.lang.Integer regionId, @org.jetbrains.annotations.Nullable
    java.lang.String status, @org.jetbrains.annotations.Nullable
    java.lang.Boolean isSynced) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getElectorId() {
        return null;
    }
    
    public final void setElectorId(@org.jetbrains.annotations.Nullable
    java.lang.Long p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getTempElectorId() {
        return null;
    }
    
    public final void setTempElectorId(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getLocationId() {
        return null;
    }
    
    public final void setLocationId(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getWardId() {
        return null;
    }
    
    public final void setWardId(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getLlgId() {
        return null;
    }
    
    public final void setLlgId(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getDistrictId() {
        return null;
    }
    
    public final void setDistrictId(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getProvinceId() {
        return null;
    }
    
    public final void setProvinceId(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getRegionId() {
        return null;
    }
    
    public final void setRegionId(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getStatus() {
        return null;
    }
    
    public final void setStatus(@org.jetbrains.annotations.Nullable
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Boolean component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Boolean isSynced() {
        return null;
    }
    
    public final void setSynced(@org.jetbrains.annotations.Nullable
    java.lang.Boolean p0) {
    }
}