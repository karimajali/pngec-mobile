package com.pngelectoral.ui.repository;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J:\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\"\u0010\t\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00020\r`\u000e0\u000b0\nJ\u001c\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\nJ2\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\"\u0010\u0013\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00140\fj\b\u0012\u0004\u0012\u00020\u0014`\u000e0\u000b0\n\u00a8\u0006\u0015"}, d2 = {"Lcom/pngelectoral/ui/repository/DownloadRepository;", "", "()V", "callDownloadVotersListAPI", "", "mContext", "Landroid/content/Context;", "userId", "", "loginResponse", "Landroidx/lifecycle/MutableLiveData;", "Lcom/pngelectoral/ui/retrofit/Response;", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/VotersList;", "Lkotlin/collections/ArrayList;", "callGetGeoListAPI", "geoListResponse", "Lcom/pngelectoral/ui/model/GeoListModel;", "callGetOccupationListAPI", "occupationListResponse", "Lcom/pngelectoral/ui/model/OccupationList;", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class DownloadRepository {
    
    public DownloadRepository() {
        super();
    }
    
    public final void callDownloadVotersListAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, @org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.VotersList>>> loginResponse) {
    }
    
    public final void callGetGeoListAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, @org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.model.GeoListModel> geoListResponse) {
    }
    
    public final void callGetOccupationListAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, @org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.OccupationList>>> occupationListResponse) {
    }
}