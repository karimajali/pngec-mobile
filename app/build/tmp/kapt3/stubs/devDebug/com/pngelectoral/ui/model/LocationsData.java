package com.pngelectoral.ui.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u001c\b\u0002\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001d\u0010\u0011\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0005H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0007H\u00c6\u0003J1\u0010\u0013\u001a\u00020\u00002\u001c\b\u0002\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R2\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001b"}, d2 = {"Lcom/pngelectoral/ui/model/LocationsData;", "Ljava/io/Serializable;", "locationsList", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/LocationList;", "Lkotlin/collections/ArrayList;", "total", "", "(Ljava/util/ArrayList;I)V", "getLocationsList", "()Ljava/util/ArrayList;", "setLocationsList", "(Ljava/util/ArrayList;)V", "getTotal", "()I", "setTotal", "(I)V", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "toString", "", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class LocationsData implements java.io.Serializable {
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "result")
    private java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locationsList;
    @com.google.gson.annotations.SerializedName(value = "total")
    private int total;
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.LocationsData copy(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locationsList, int total) {
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
    
    public LocationsData() {
        super();
    }
    
    public LocationsData(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locationsList, int total) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.LocationList> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.LocationList> getLocationsList() {
        return null;
    }
    
    public final void setLocationsList(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.LocationList> p0) {
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getTotal() {
        return 0;
    }
    
    public final void setTotal(int p0) {
    }
}