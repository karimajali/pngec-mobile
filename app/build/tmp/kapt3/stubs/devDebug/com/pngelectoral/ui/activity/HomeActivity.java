package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010)\u001a\u00020*H\u0002J\b\u0010+\u001a\u00020*H\u0002J\b\u0010,\u001a\u00020*H\u0002J\u0010\u0010-\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010.H\u0002J\u0010\u00100\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010.H\u0002J\u0014\u00101\u001a\u0002022\n\u00103\u001a\u0006\u0012\u0002\b\u000304H\u0002J\u0018\u00105\u001a\u0002022\u0006\u00106\u001a\u00020\u00142\u0006\u00107\u001a\u000202H\u0002J\u0012\u00108\u001a\u00020*2\b\u00109\u001a\u0004\u0018\u00010\u0014H\u0002J\b\u0010:\u001a\u00020*H\u0002J\b\u0010;\u001a\u00020*H\u0002J\u0010\u0010<\u001a\u00020*2\u0006\u0010=\u001a\u00020\u0014H\u0002J\u0012\u0010>\u001a\u00020*2\b\u0010?\u001a\u0004\u0018\u00010@H\u0014J\b\u0010A\u001a\u00020*H\u0014J\u0018\u0010B\u001a\u00020*2\u000e\u0010C\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010.H\u0002J\b\u0010D\u001a\u00020*H\u0002J\b\u0010E\u001a\u00020*H\u0002J\u0010\u0010F\u001a\u00020*2\u0006\u0010G\u001a\u00020\u0014H\u0002J\u0018\u0010H\u001a\u00020*2\u0006\u00107\u001a\u0002022\u0006\u0010=\u001a\u00020\u0014H\u0002J\u0010\u0010I\u001a\u00020*2\u0006\u0010=\u001a\u00020\u0014H\u0002J\u0010\u0010J\u001a\u00020*2\u0006\u0010G\u001a\u00020\u0014H\u0002J\b\u0010K\u001a\u00020*H\u0002J\u0010\u0010L\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010.H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00140\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\u00020\u001dX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010$\u001a\u00020\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(\u00a8\u0006M"}, d2 = {"Lcom/pngelectoral/ui/activity/HomeActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityHomeBinding;", "bindingSyncSettingsPassword", "Lcom/pngelectoral/databinding/DialogSyncSettingsPasswordBinding;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "homeMenuAdapter", "Lcom/pngelectoral/ui/adapter/GenericAdapter;", "", "Lcom/pngelectoral/databinding/ItemHomeBinding;", "homePageMenu", "", "homeViewModel", "Lcom/pngelectoral/ui/viewModel/HomeViewModel;", "job", "Lkotlinx/coroutines/Job;", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "getLoggedInDPOUserData", "()Lcom/pngelectoral/ui/model/DPOUserData;", "setLoggedInDPOUserData", "(Lcom/pngelectoral/ui/model/DPOUserData;)V", "mSyncAndSettingsAlertDialog", "Landroid/app/AlertDialog;", "provinceName", "getProvinceName", "()Ljava/lang/String;", "setProvinceName", "(Ljava/lang/String;)V", "addObservers", "", "checkNetWorkAndCallLogoutAPI", "checkVotersToSync", "getAllVotersList", "", "Lcom/pngelectoral/ui/model/VotersList;", "getVotersListToSync", "isMyServiceRunning", "", "serviceClass", "Ljava/lang/Class;", "isValidPassword", "password", "isSync", "navigateToClickedModule", "menuItem", "navigateToLogin", "navigateToSettingsScreen", "navigateToSyncScreen", "filterType", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "proceedToLogoutFlow", "result", "setHeaderAndFooterData", "setHomePageAdapter", "showSelectWorkingRollFilterAlert", "message", "showSyncAndSettingsPasswordOption", "showSyncSelectionOptions", "showValidationMessage", "stopAnyRunningService", "validateRollAndSync", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class HomeActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivityHomeBinding binding;
    private com.pngelectoral.ui.viewModel.HomeViewModel homeViewModel;
    public com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private java.util.List<java.lang.String> homePageMenu;
    private com.pngelectoral.ui.adapter.GenericAdapter<java.lang.String, com.pngelectoral.databinding.ItemHomeBinding> homeMenuAdapter;
    @org.jetbrains.annotations.NotNull
    private java.lang.String provinceName = "";
    private kotlinx.coroutines.Job job;
    private android.app.AlertDialog mSyncAndSettingsAlertDialog;
    private com.pngelectoral.databinding.DialogSyncSettingsPasswordBinding bindingSyncSettingsPassword;
    private java.util.HashMap _$_findViewCache;
    
    public HomeActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.DPOUserData getLoggedInDPOUserData() {
        return null;
    }
    
    public final void setLoggedInDPOUserData(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.DPOUserData p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.data.db.AppDatabase getDataBaseInstance() {
        return null;
    }
    
    public final void setDataBaseInstance(@org.jetbrains.annotations.NotNull
    com.pngelectoral.data.db.AppDatabase p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getProvinceName() {
        return null;
    }
    
    public final void setProvinceName(@org.jetbrains.annotations.NotNull
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
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    private final void setHeaderAndFooterData() {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.VotersList> getAllVotersList() {
        return null;
    }
    
    private final void setHomePageAdapter() {
    }
    
    private final void navigateToClickedModule(java.lang.String menuItem) {
    }
    
    private final void showSelectWorkingRollFilterAlert(java.lang.String message) {
    }
    
    private final void showSyncAndSettingsPasswordOption(boolean isSync, java.lang.String filterType) {
    }
    
    private final boolean isValidPassword(java.lang.String password, boolean isSync) {
        return false;
    }
    
    private final void navigateToSettingsScreen() {
    }
    
    private final void navigateToSyncScreen(java.lang.String filterType) {
    }
    
    private final void showSyncSelectionOptions(java.lang.String filterType) {
    }
    
    private final void showValidationMessage(java.lang.String message) {
    }
    
    private final void checkVotersToSync() {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.VotersList> getVotersListToSync() {
        return null;
    }
    
    private final void proceedToLogoutFlow(java.util.List<? extends com.pngelectoral.ui.model.VotersList> result) {
    }
    
    private final void checkNetWorkAndCallLogoutAPI() {
    }
    
    private final void addObservers() {
    }
    
    private final void stopAnyRunningService() {
    }
    
    private final boolean isMyServiceRunning(java.lang.Class<?> serviceClass) {
        return false;
    }
    
    private final void navigateToLogin() {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.VotersList> validateRollAndSync() {
        return null;
    }
}