package com.pngelectoral.ui.viewModel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0018"}, d2 = {"Lcom/pngelectoral/ui/viewModel/DpoGeoStructureUpdateViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "app", "Landroid/app/Application;", "(Landroid/app/Application;)V", "getApp", "()Landroid/app/Application;", "setApp", "dpoGeoStructureUpdateRepository", "Lcom/pngelectoral/ui/repository/DPOGeoStructureUpdateRepository;", "dpoGeoStructureUpdateResponse", "Landroidx/lifecycle/MutableLiveData;", "Lcom/pngelectoral/ui/retrofit/Response;", "", "getDpoGeoStructureUpdateResponse", "()Landroidx/lifecycle/MutableLiveData;", "setDpoGeoStructureUpdateResponse", "(Landroidx/lifecycle/MutableLiveData;)V", "callUpdateGeoStructureAPI", "", "mContext", "Landroid/content/Context;", "dpoGeoStructureUpdateObj", "Lcom/google/gson/JsonObject;", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class DpoGeoStructureUpdateViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull
    private android.app.Application app;
    @org.jetbrains.annotations.NotNull
    private androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.lang.Object>> dpoGeoStructureUpdateResponse;
    private final com.pngelectoral.ui.repository.DPOGeoStructureUpdateRepository dpoGeoStructureUpdateRepository = null;
    
    public DpoGeoStructureUpdateViewModel(@org.jetbrains.annotations.NotNull
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
    public final androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.lang.Object>> getDpoGeoStructureUpdateResponse() {
        return null;
    }
    
    public final void setDpoGeoStructureUpdateResponse(@org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.lang.Object>> p0) {
    }
    
    public final void callUpdateGeoStructureAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, @org.jetbrains.annotations.NotNull
    com.google.gson.JsonObject dpoGeoStructureUpdateObj) {
    }
}