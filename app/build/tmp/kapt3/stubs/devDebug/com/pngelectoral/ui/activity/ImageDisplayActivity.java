package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\"H\u0002J\b\u0010$\u001a\u00020\"H\u0002J\b\u0010%\u001a\u00020\"H\u0016J\u0012\u0010&\u001a\u00020\"2\b\u0010\'\u001a\u0004\u0018\u00010(H\u0015J\b\u0010)\u001a\u00020\"H\u0002J\b\u0010*\u001a\u00020\"H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0012\"\u0004\b\u0016\u0010\u0014R\u0016\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/pngelectoral/ui/activity/ImageDisplayActivity;", "Lcom/pngelectoral/base/BaseActivity;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityImageDisplayBinding;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "form11ImagePath", "", "imageDisplayViewModel", "Lcom/pngelectoral/ui/viewModel/ImageDisplayViewModel;", "isFromForm11", "", "isFromNewEnrolment", "()Z", "setFromNewEnrolment", "(Z)V", "isFromTransferIn", "setFromTransferIn", "storagePermissionResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "voterDetailsResponse", "Lcom/pngelectoral/ui/model/VotersList;", "getVoterDetailsResponse", "()Lcom/pngelectoral/ui/model/VotersList;", "setVoterDetailsResponse", "(Lcom/pngelectoral/ui/model/VotersList;)V", "voterImagePath", "checkManageAllPermission", "", "navigateToDigitalFormScreen", "navigateToVoterImageCaptureScreen", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "requestCameraPermission", "setFooterData", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class ImageDisplayActivity extends com.pngelectoral.base.BaseActivity {
    private com.pngelectoral.databinding.ActivityImageDisplayBinding binding;
    private com.pngelectoral.ui.viewModel.ImageDisplayViewModel imageDisplayViewModel;
    private boolean isFromForm11 = true;
    private java.lang.String voterImagePath = "";
    private java.lang.String form11ImagePath = "";
    private boolean isFromTransferIn = false;
    private boolean isFromNewEnrolment = false;
    @org.jetbrains.annotations.NotNull
    private com.pngelectoral.ui.model.VotersList voterDetailsResponse;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> storagePermissionResultLauncher;
    private java.util.HashMap _$_findViewCache;
    
    public ImageDisplayActivity() {
        super();
    }
    
    public final boolean isFromTransferIn() {
        return false;
    }
    
    public final void setFromTransferIn(boolean p0) {
    }
    
    public final boolean isFromNewEnrolment() {
        return false;
    }
    
    public final void setFromNewEnrolment(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.VotersList getVoterDetailsResponse() {
        return null;
    }
    
    public final void setVoterDetailsResponse(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.VotersList p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.data.db.AppDatabase getDataBaseInstance() {
        return null;
    }
    
    public final void setDataBaseInstance(@org.jetbrains.annotations.NotNull
    com.pngelectoral.data.db.AppDatabase p0) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setFooterData() {
    }
    
    private final void navigateToDigitalFormScreen() {
    }
    
    private final void navigateToVoterImageCaptureScreen() {
    }
    
    private final void requestCameraPermission() {
    }
    
    private final void checkManageAllPermission() {
    }
    
    @java.lang.Override
    public void onBackPressed() {
    }
}