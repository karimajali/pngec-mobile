package com.pngelectoral.ui.viewModel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\"\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R,\u0010\u0011\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u00120\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0010R,\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00130\u00120\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u000e\"\u0004\b\u001a\u0010\u0010\u00a8\u0006#"}, d2 = {"Lcom/pngelectoral/ui/viewModel/DownloadViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "app", "Landroid/app/Application;", "(Landroid/app/Application;)V", "getApp", "()Landroid/app/Application;", "setApp", "downloadRepository", "Lcom/pngelectoral/ui/repository/DownloadRepository;", "geoListResponse", "Landroidx/lifecycle/MutableLiveData;", "Lcom/pngelectoral/ui/model/GeoListModel;", "getGeoListResponse", "()Landroidx/lifecycle/MutableLiveData;", "setGeoListResponse", "(Landroidx/lifecycle/MutableLiveData;)V", "occupationListResponse", "Lcom/pngelectoral/ui/retrofit/Response;", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/OccupationList;", "getOccupationListResponse", "setOccupationListResponse", "votersListResponse", "Lcom/pngelectoral/ui/model/VotersList;", "getVotersListResponse", "setVotersListResponse", "callDownloadVotersListAPI", "", "mContext", "Landroid/content/Context;", "userId", "", "callGetGeoListAPI", "callGetOccupationListAPI", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class DownloadViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull
    private android.app.Application app;
    @org.jetbrains.annotations.NotNull
    private androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.VotersList>>> votersListResponse;
    @org.jetbrains.annotations.NotNull
    private androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.model.GeoListModel> geoListResponse;
    @org.jetbrains.annotations.NotNull
    private androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.OccupationList>>> occupationListResponse;
    private final com.pngelectoral.ui.repository.DownloadRepository downloadRepository = null;
    
    public DownloadViewModel(@org.jetbrains.annotations.NotNull
    android.app.Application app) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.app.Application getApp() {
        return null;
    }
    
    public final void setApp(@org.jetbrains.annotations.NotNull
    android.app.Application p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.VotersList>>> getVotersListResponse() {
        return null;
    }
    
    public final void setVotersListResponse(@org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.VotersList>>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.model.GeoListModel> getGeoListResponse() {
        return null;
    }
    
    public final void setGeoListResponse(@org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.model.GeoListModel> p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.OccupationList>>> getOccupationListResponse() {
        return null;
    }
    
    public final void setOccupationListResponse(@org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.OccupationList>>> p0) {
    }
    
    public final void callDownloadVotersListAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, @org.jetbrains.annotations.NotNull
    java.lang.String userId) {
    }
    
    public final void callGetGeoListAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext) {
    }
    
    public final void callGetOccupationListAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext) {
    }
}