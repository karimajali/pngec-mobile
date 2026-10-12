package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u00108\u001a\u000209H\u0002J\b\u0010:\u001a\u000209H\u0002J\b\u0010;\u001a\u000209H\u0002J\u0012\u0010<\u001a\u0002092\b\u0010=\u001a\u0004\u0018\u000103H\u0002J\u0012\u0010>\u001a\u0002092\b\u0010=\u001a\u0004\u0018\u000103H\u0002J\u0018\u0010?\u001a\u0002092\u0006\u0010@\u001a\u0002032\u0006\u0010A\u001a\u000203H\u0002J\b\u0010B\u001a\u000209H\u0002J\u0014\u0010C\u001a\u00020\u000b2\n\u0010D\u001a\u0006\u0012\u0002\b\u00030EH\u0002J\b\u0010F\u001a\u000209H\u0002J\u0012\u0010G\u001a\u0002092\b\u0010H\u001a\u0004\u0018\u00010IH\u0014J\b\u0010J\u001a\u000209H\u0014J\b\u0010K\u001a\u000209H\u0002J\b\u0010L\u001a\u000209H\u0002J\u0010\u0010M\u001a\u0002092\u0006\u0010N\u001a\u000203H\u0002J\b\u0010O\u001a\u000209H\u0002J\b\u0010P\u001a\u000209H\u0002J\u001e\u0010Q\u001a\u0002092\u0006\u0010R\u001a\u00020S2\f\u0010D\u001a\b\u0012\u0002\b\u0003\u0018\u00010EH\u0002J \u0010T\u001a\u0002092\u0006\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u0002032\u0006\u0010X\u001a\u00020VH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R\u001c\u0010)\u001a\u0004\u0018\u00010$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010&\"\u0004\b+\u0010(R\u001a\u0010,\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u000e\u00101\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u00102\u001a\u000203X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107\u00a8\u0006Y"}, d2 = {"Lcom/pngelectoral/ui/activity/NewDownloadActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "allPageDownloadReceiver", "Landroid/content/BroadcastReceiver;", "binding", "Lcom/pngelectoral/databinding/ActivityDownloadBinding;", "bindingLayoutLoaderDownloadProgressBar", "Lcom/pngelectoral/databinding/LayoutLoaderDownloadProgressBarBinding;", "clearDataDone", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "downloadFileProgressBinding", "Lcom/pngelectoral/databinding/DownloadFileProgressBinding;", "downloadViewModel", "Lcom/pngelectoral/ui/viewModel/DownloadViewModel;", "fetch", "Lcom/tonyodev/fetch2/Fetch;", "fileDownloadedProgressReceiver", "fileDownloadedReceiver", "internetConnectionStatusReceiver", "job", "Lkotlinx/coroutines/Job;", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "mDownloadProgressDialog", "Landroid/app/AlertDialog;", "getMDownloadProgressDialog", "()Landroid/app/AlertDialog;", "setMDownloadProgressDialog", "(Landroid/app/AlertDialog;)V", "mFileDownloadProgressDialog", "getMFileDownloadProgressDialog", "setMFileDownloadProgressDialog", "showingInternetConnectionMessage", "getShowingInternetConnectionMessage", "()Z", "setShowingInternetConnectionMessage", "(Z)V", "singlePageDownloadedReceiver", "zipFilePath", "", "getZipFilePath", "()Ljava/lang/String;", "setZipFilePath", "(Ljava/lang/String;)V", "callDownloadVotersListFile", "", "checkManageAllPermission", "clearData", "downloadFile", "url", "downloadFileUsingFetch", "handleDirectory", "dir", "location", "hideProgressAndNavigateToNextScreen", "isMyServiceRunning", "serviceClass", "Ljava/lang/Class;", "navigateToWardRollScreen", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "requestPermission", "setClickListeners", "showAlertMessage", "alertMessage", "showDownloadProgressDialog", "showFileDownloadProgressDialog", "startRequiredService", "activity", "Landroid/app/Activity;", "unZipTheDownloadedFile", "folder", "Ljava/io/File;", "fileName", "file", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class NewDownloadActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivityDownloadBinding binding;
    private com.pngelectoral.ui.viewModel.DownloadViewModel downloadViewModel;
    private com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private boolean clearDataDone = false;
    private kotlinx.coroutines.Job job;
    @org.jetbrains.annotations.NotNull
    private java.lang.String zipFilePath = "";
    private com.tonyodev.fetch2.Fetch fetch;
    private com.pngelectoral.databinding.DownloadFileProgressBinding downloadFileProgressBinding;
    @org.jetbrains.annotations.Nullable
    private android.app.AlertDialog mFileDownloadProgressDialog;
    private final android.content.BroadcastReceiver fileDownloadedProgressReceiver = null;
    private final android.content.BroadcastReceiver fileDownloadedReceiver = null;
    private final android.content.BroadcastReceiver allPageDownloadReceiver = null;
    private final android.content.BroadcastReceiver singlePageDownloadedReceiver = null;
    private boolean showingInternetConnectionMessage = false;
    private final android.content.BroadcastReceiver internetConnectionStatusReceiver = null;
    private com.pngelectoral.databinding.LayoutLoaderDownloadProgressBarBinding bindingLayoutLoaderDownloadProgressBar;
    @org.jetbrains.annotations.Nullable
    private android.app.AlertDialog mDownloadProgressDialog;
    private java.util.HashMap _$_findViewCache;
    
    public NewDownloadActivity() {
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
    @java.lang.Override
    public kotlin.coroutines.CoroutineContext getCoroutineContext() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void clearData() {
    }
    
    private final void setClickListeners() {
    }
    
    private final void requestPermission() {
    }
    
    private final void checkManageAllPermission() {
    }
    
    private final void callDownloadVotersListFile() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getZipFilePath() {
        return null;
    }
    
    public final void setZipFilePath(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    private final void downloadFile(java.lang.String url) {
    }
    
    private final void downloadFileUsingFetch(java.lang.String url) {
    }
    
    private final void unZipTheDownloadedFile(java.io.File folder, java.lang.String fileName, java.io.File file) {
    }
    
    private final void handleDirectory(java.lang.String dir, java.lang.String location) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.app.AlertDialog getMFileDownloadProgressDialog() {
        return null;
    }
    
    public final void setMFileDownloadProgressDialog(@org.jetbrains.annotations.Nullable
    android.app.AlertDialog p0) {
    }
    
    private final void showFileDownloadProgressDialog() {
    }
    
    public final boolean getShowingInternetConnectionMessage() {
        return false;
    }
    
    public final void setShowingInternetConnectionMessage(boolean p0) {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.app.AlertDialog getMDownloadProgressDialog() {
        return null;
    }
    
    public final void setMDownloadProgressDialog(@org.jetbrains.annotations.Nullable
    android.app.AlertDialog p0) {
    }
    
    private final void showDownloadProgressDialog() {
    }
    
    private final void hideProgressAndNavigateToNextScreen() {
    }
    
    private final void navigateToWardRollScreen() {
    }
    
    private final void showAlertMessage(java.lang.String alertMessage) {
    }
    
    private final void startRequiredService(android.app.Activity activity, java.lang.Class<?> serviceClass) {
    }
    
    private final boolean isMyServiceRunning(java.lang.Class<?> serviceClass) {
        return false;
    }
}