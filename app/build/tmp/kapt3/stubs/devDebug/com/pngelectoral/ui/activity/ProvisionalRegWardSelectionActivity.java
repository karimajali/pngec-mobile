package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u00108\u001a\u000209H\u0003J\b\u0010:\u001a\u000209H\u0003J\u0012\u0010;\u001a\u0002092\b\u0010<\u001a\u0004\u0018\u00010=H\u0014J\b\u0010>\u001a\u000209H\u0014J\b\u0010?\u001a\u000209H\u0002J\b\u0010@\u001a\u000209H\u0002J\b\u0010A\u001a\u000209H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0013\"\u0004\b\u001a\u0010\u0015R\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010\'\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0013\"\u0004\b)\u0010\u0015R\u001a\u0010*\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0013\"\u0004\b,\u0010\u0015R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010/\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u0013\"\u0004\b1\u0010\u0015R\u001a\u00102\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u001e\"\u0004\b4\u0010 R\u001a\u00105\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u0013\"\u0004\b7\u0010\u0015\u00a8\u0006B"}, d2 = {"Lcom/pngelectoral/ui/activity/ProvisionalRegWardSelectionActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityProvisionalRegWardSelectionBinding;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "districtId", "", "getDistrictId", "()I", "setDistrictId", "(I)V", "job", "Lkotlinx/coroutines/Job;", "llgId", "getLlgId", "setLlgId", "llgName", "", "getLlgName", "()Ljava/lang/String;", "setLlgName", "(Ljava/lang/String;)V", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "getLoggedInDPOUserData", "()Lcom/pngelectoral/ui/model/DPOUserData;", "setLoggedInDPOUserData", "(Lcom/pngelectoral/ui/model/DPOUserData;)V", "provinceId", "getProvinceId", "setProvinceId", "regionId", "getRegionId", "setRegionId", "registrationViewModel", "Lcom/pngelectoral/ui/viewModel/RegistrationViewModel;", "wardId", "getWardId", "setWardId", "wardName", "getWardName", "setWardName", "wardNumber", "getWardNumber", "setWardNumber", "getMKAWardNumberAndNameUsingCoroutines", "", "getWardNumberAndNameUsingCoroutines", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "proceedToProvisionalRegistration", "setFooterData", "setOnclickListeners", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class ProvisionalRegWardSelectionActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivityProvisionalRegWardSelectionBinding binding;
    private com.pngelectoral.ui.viewModel.RegistrationViewModel registrationViewModel;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    public com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    private int regionId = 0;
    private int provinceId = 0;
    private int districtId = 0;
    private int llgId = 0;
    private int wardId = 0;
    @org.jetbrains.annotations.NotNull
    private java.lang.String llgName = "";
    private int wardNumber = 0;
    @org.jetbrains.annotations.NotNull
    private java.lang.String wardName = "";
    private kotlinx.coroutines.Job job;
    private java.util.HashMap _$_findViewCache;
    
    public ProvisionalRegWardSelectionActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.data.db.AppDatabase getDataBaseInstance() {
        return null;
    }
    
    public final void setDataBaseInstance(@org.jetbrains.annotations.NotNull
    com.pngelectoral.data.db.AppDatabase p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.DPOUserData getLoggedInDPOUserData() {
        return null;
    }
    
    public final void setLoggedInDPOUserData(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.DPOUserData p0) {
    }
    
    public final int getRegionId() {
        return 0;
    }
    
    public final void setRegionId(int p0) {
    }
    
    public final int getProvinceId() {
        return 0;
    }
    
    public final void setProvinceId(int p0) {
    }
    
    public final int getDistrictId() {
        return 0;
    }
    
    public final void setDistrictId(int p0) {
    }
    
    public final int getLlgId() {
        return 0;
    }
    
    public final void setLlgId(int p0) {
    }
    
    public final int getWardId() {
        return 0;
    }
    
    public final void setWardId(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLlgName() {
        return null;
    }
    
    public final void setLlgName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    public final int getWardNumber() {
        return 0;
    }
    
    public final void setWardNumber(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWardName() {
        return null;
    }
    
    public final void setWardName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public kotlin.coroutines.CoroutineContext getCoroutineContext() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setFooterData() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void getWardNumberAndNameUsingCoroutines() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void getMKAWardNumberAndNameUsingCoroutines() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    private final void setOnclickListeners() {
    }
    
    private final void proceedToProvisionalRegistration() {
    }
}