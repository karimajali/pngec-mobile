package com.pngelectoral.ui.repository;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ*\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\b2\u0012\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000f0\n\u00a8\u0006\u0010"}, d2 = {"Lcom/pngelectoral/ui/repository/SettingsRepository;", "", "()V", "callGetUpdatedVoterListAPI", "", "mContext", "Landroid/content/Context;", "downloadStagingVotersDataObj", "Lcom/google/gson/JsonObject;", "updatedVotersListResponse", "Landroidx/lifecycle/MutableLiveData;", "Lcom/pngelectoral/ui/model/TransferInVotersModel;", "callLogoutAPI", "inputParam", "logoutResponse", "Lcom/pngelectoral/ui/retrofit/Response;", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class SettingsRepository {
    
    public SettingsRepository() {
        super();
    }
    
    public final void callLogoutAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, @org.jetbrains.annotations.NotNull
    com.google.gson.JsonObject inputParam, @org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.lang.Object>> logoutResponse) {
    }
    
    public final void callGetUpdatedVoterListAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, @org.jetbrains.annotations.NotNull
    com.google.gson.JsonObject downloadStagingVotersDataObj, @org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.model.TransferInVotersModel> updatedVotersListResponse) {
    }
}