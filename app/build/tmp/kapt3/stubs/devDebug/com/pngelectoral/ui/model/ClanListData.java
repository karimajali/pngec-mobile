package com.pngelectoral.ui.model;

import java.lang.System;

/**
 * Model class created for table in database
 */
@androidx.room.Entity(tableName = "clanlist")
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR \u0010\u000f\u001a\u0004\u0018\u00010\n8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\"\u0010\u0012\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0017\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0018"}, d2 = {"Lcom/pngelectoral/ui/model/ClanListData;", "Ljava/io/Serializable;", "()V", "clanId", "", "getClanId", "()I", "setClanId", "(I)V", "clanName", "", "getClanName", "()Ljava/lang/String;", "setClanName", "(Ljava/lang/String;)V", "description", "getDescription", "setDescription", "wardId", "getWardId", "()Ljava/lang/Integer;", "setWardId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class ClanListData implements java.io.Serializable {
    @androidx.room.ColumnInfo(name = "clanId")
    @androidx.room.PrimaryKey
    private int clanId = 0;
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "wardId")
    private java.lang.Integer wardId = 0;
    @org.jetbrains.annotations.NotNull
    @androidx.room.ColumnInfo(name = "clanName")
    private java.lang.String clanName = "";
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "description")
    private java.lang.String description = "";
    
    public ClanListData() {
        super();
    }
    
    public final int getClanId() {
        return 0;
    }
    
    public final void setClanId(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getWardId() {
        return null;
    }
    
    public final void setWardId(@org.jetbrains.annotations.Nullable
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getClanName() {
        return null;
    }
    
    public final void setClanName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final void setDescription(@org.jetbrains.annotations.Nullable
    java.lang.String p0) {
    }
}