package com.pngelectoral.ui.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u009b\u0001\u0012\u001c\b\u0002\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0005\u0012\u001c\b\u0002\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0007\u0018\u0001`\u0005\u0012\u001c\b\u0002\u0010\b\u001a\u0016\u0012\u0004\u0012\u00020\t\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\t\u0018\u0001`\u0005\u0012\u001c\b\u0002\u0010\n\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u000b\u0018\u0001`\u0005\u0012\u001c\b\u0002\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\r\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\r\u0018\u0001`\u0005\u00a2\u0006\u0002\u0010\u000eJ\u001d\u0010\u001b\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0005H\u00c6\u0003J\u001d\u0010\u001c\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0007\u0018\u0001`\u0005H\u00c6\u0003J\u001d\u0010\u001d\u001a\u0016\u0012\u0004\u0012\u00020\t\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\t\u0018\u0001`\u0005H\u00c6\u0003J\u001d\u0010\u001e\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u000b\u0018\u0001`\u0005H\u00c6\u0003J\u001d\u0010\u001f\u001a\u0016\u0012\u0004\u0012\u00020\r\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\r\u0018\u0001`\u0005H\u00c6\u0003J\u009f\u0001\u0010 \u001a\u00020\u00002\u001c\b\u0002\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u00052\u001c\b\u0002\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0007\u0018\u0001`\u00052\u001c\b\u0002\u0010\b\u001a\u0016\u0012\u0004\u0012\u00020\t\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\t\u0018\u0001`\u00052\u001c\b\u0002\u0010\n\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u000b\u0018\u0001`\u00052\u001c\b\u0002\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\r\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\r\u0018\u0001`\u0005H\u00c6\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010\'\u001a\u00020(H\u00d6\u0001R2\u0010\b\u001a\u0016\u0012\u0004\u0012\u00020\t\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\t\u0018\u0001`\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R2\u0010\n\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u000b\u0018\u0001`\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0010\"\u0004\b\u0014\u0010\u0012R2\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0007\u0018\u0001`\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0010\"\u0004\b\u0016\u0010\u0012R2\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R2\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\r\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\r\u0018\u0001`\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0010\"\u0004\b\u001a\u0010\u0012\u00a8\u0006)"}, d2 = {"Lcom/pngelectoral/ui/model/GeoListData;", "Ljava/io/Serializable;", "regionList", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/RegionList;", "Lkotlin/collections/ArrayList;", "provinceList", "Lcom/pngelectoral/ui/model/ProvinceList;", "districtList", "Lcom/pngelectoral/ui/model/DistrictList;", "llgList", "Lcom/pngelectoral/ui/model/LLGList;", "wardList", "Lcom/pngelectoral/ui/model/WardList;", "(Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/util/ArrayList;)V", "getDistrictList", "()Ljava/util/ArrayList;", "setDistrictList", "(Ljava/util/ArrayList;)V", "getLlgList", "setLlgList", "getProvinceList", "setProvinceList", "getRegionList", "setRegionList", "getWardList", "setWardList", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class GeoListData implements java.io.Serializable {
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "regionList")
    private java.util.ArrayList<com.pngelectoral.ui.model.RegionList> regionList;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "provinceList")
    private java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> provinceList;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "districtList")
    private java.util.ArrayList<com.pngelectoral.ui.model.DistrictList> districtList;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "llgList")
    private java.util.ArrayList<com.pngelectoral.ui.model.LLGList> llgList;
    @org.jetbrains.annotations.Nullable
    @com.google.gson.annotations.SerializedName(value = "wardList")
    private java.util.ArrayList<com.pngelectoral.ui.model.WardList> wardList;
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.GeoListData copy(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.RegionList> regionList, @org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> provinceList, @org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.DistrictList> districtList, @org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.LLGList> llgList, @org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.WardList> wardList) {
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
    
    public GeoListData() {
        super();
    }
    
    public GeoListData(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.RegionList> regionList, @org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> provinceList, @org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.DistrictList> districtList, @org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.LLGList> llgList, @org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.WardList> wardList) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.RegionList> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.RegionList> getRegionList() {
        return null;
    }
    
    public final void setRegionList(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.RegionList> p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> getProvinceList() {
        return null;
    }
    
    public final void setProvinceList(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.DistrictList> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.DistrictList> getDistrictList() {
        return null;
    }
    
    public final void setDistrictList(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.DistrictList> p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.LLGList> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.LLGList> getLlgList() {
        return null;
    }
    
    public final void setLlgList(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.LLGList> p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.WardList> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.WardList> getWardList() {
        return null;
    }
    
    public final void setWardList(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.WardList> p0) {
    }
}