package com.pngelectoral.ui.viewModel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u0016\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R&\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u000f\u00a8\u0006\u001e"}, d2 = {"Lcom/pngelectoral/ui/viewModel/SettingsViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "app", "Landroid/app/Application;", "(Landroid/app/Application;)V", "getApp", "()Landroid/app/Application;", "setApp", "logoutResponse", "Landroidx/lifecycle/MutableLiveData;", "Lcom/pngelectoral/ui/retrofit/Response;", "", "getLogoutResponse", "()Landroidx/lifecycle/MutableLiveData;", "setLogoutResponse", "(Landroidx/lifecycle/MutableLiveData;)V", "settingsRepository", "Lcom/pngelectoral/ui/repository/SettingsRepository;", "updatedVotersListResponse", "Lcom/pngelectoral/ui/model/TransferInVotersModel;", "getUpdatedVotersListResponse", "setUpdatedVotersListResponse", "callGetUpdatedVoterListAPI", "", "mContext", "Landroid/content/Context;", "downloadStagingVotersDataObj", "Lcom/google/gson/JsonObject;", "callLogoutAPI", "inputParam", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class SettingsViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull
    private android.app.Application app;
    private final com.pngelectoral.ui.repository.SettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull
    private androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.lang.Object>> logoutResponse;
    @org.jetbrains.annotations.NotNull
    private androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.model.TransferInVotersModel> updatedVotersListResponse;
    
    public SettingsViewModel(@org.jetbrains.annotations.NotNull
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
    public final androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.lang.Object>> getLogoutResponse() {
        return null;
    }
    
    public final void setLogoutResponse(@org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.lang.Object>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.model.TransferInVotersModel> getUpdatedVotersListResponse() {
        return null;
    }
    
    public final void setUpdatedVotersListResponse(@org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.model.TransferInVotersModel> p0) {
    }
    
    public final void callLogoutAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, @org.jetbrains.annotations.NotNull
    com.google.gson.JsonObject inputParam) {
    }
    
    public final void callGetUpdatedVoterListAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, @org.jetbrains.annotations.NotNull
    com.google.gson.JsonObject downloadStagingVotersDataObj) {
    }
}