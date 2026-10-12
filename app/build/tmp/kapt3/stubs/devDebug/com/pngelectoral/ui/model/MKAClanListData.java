package com.pngelectoral.ui.model;

import java.lang.System;

/**
 * Model class created for table in database
 */
@androidx.room.Entity(tableName = "mkaclanlist")
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\"\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\t\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR \u0010\u0010\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR\"\u0010\u0013\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\t\u001a\u0004\b\u0014\u0010\u0006\"\u0004\b\u0015\u0010\b\u00a8\u0006\u0016"}, d2 = {"Lcom/pngelectoral/ui/model/MKAClanListData;", "Ljava/io/Serializable;", "()V", "clanId", "", "getClanId", "()Ljava/lang/Integer;", "setClanId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "clanName", "", "getClanName", "()Ljava/lang/String;", "setClanName", "(Ljava/lang/String;)V", "description", "getDescription", "setDescription", "responseCode", "getResponseCode", "setResponseCode", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class MKAClanListData implements java.io.Serializable {
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "clanId")
    @androidx.room.PrimaryKey
    private java.lang.Integer clanId = 0;
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "clanName")
    private java.lang.String clanName = "";
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "description")
    private java.lang.String description = "";
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "responseCode")
    private java.lang.Integer responseCode = 0;
    
    public MKAClanListData() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getClanId() {
        return null;
    }
    
    public final void setClanId(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getClanName() {
        return null;
    }
    
    public final void setClanName(@org.jetbrains.annotations.Nullable
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final void setDescription(@org.jetbrains.annotations.Nullable
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getResponseCode() {
        return null;
    }
    
    public final void setResponseCode(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
}