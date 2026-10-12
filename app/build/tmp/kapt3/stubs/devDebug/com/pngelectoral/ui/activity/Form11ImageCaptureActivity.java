package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\"H\u0002J\b\u0010&\u001a\u00020\"H\u0002J\b\u0010\'\u001a\u00020\"H\u0002J\"\u0010(\u001a\u00020\"2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\b\u0010,\u001a\u0004\u0018\u00010-H\u0014J\u0012\u0010.\u001a\u00020\"2\b\u0010/\u001a\u0004\u0018\u000100H\u0014J-\u00101\u001a\u00020\"2\u0006\u0010)\u001a\u00020*2\u000e\u00102\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f032\u0006\u00104\u001a\u000205H\u0016\u00a2\u0006\u0002\u00106J\b\u00107\u001a\u00020\"H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 \u00a8\u00068"}, d2 = {"Lcom/pngelectoral/ui/activity/Form11ImageCaptureActivity;", "Lcom/pngelectoral/base/BaseActivity;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityForm11ImageCaptureBinding;", "form11ImageCaptureViewModel", "Lcom/pngelectoral/ui/viewModel/Form11ImageCaptureViewModel;", "isFromNewEnrolment", "", "()Z", "setFromNewEnrolment", "(Z)V", "isFromTransferIn", "setFromTransferIn", "localImagePath", "", "getLocalImagePath", "()Ljava/lang/String;", "setLocalImagePath", "(Ljava/lang/String;)V", "scopedMediaPicker", "Lcom/pngelectoral/Form11ImageHelper/ScopedMediaPicker;", "getScopedMediaPicker", "()Lcom/pngelectoral/Form11ImageHelper/ScopedMediaPicker;", "scopedMediaPicker$delegate", "Lkotlin/Lazy;", "selectedImagePath", "voterDetailsResponse", "Lcom/pngelectoral/ui/model/VotersList;", "getVoterDetailsResponse", "()Lcom/pngelectoral/ui/model/VotersList;", "setVoterDetailsResponse", "(Lcom/pngelectoral/ui/model/VotersList;)V", "beginCrop", "", "source", "Landroid/net/Uri;", "captureForm11Image", "captureImage", "checkManageAllPermission", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "requestCameraPermission", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class Form11ImageCaptureActivity extends com.pngelectoral.base.BaseActivity {
    private com.pngelectoral.databinding.ActivityForm11ImageCaptureBinding binding;
    private com.pngelectoral.ui.viewModel.Form11ImageCaptureViewModel form11ImageCaptureViewModel;
    @org.jetbrains.annotations.Nullable
    private java.lang.String localImagePath;
    private boolean isFromTransferIn = false;
    private boolean isFromNewEnrolment = false;
    @org.jetbrains.annotations.NotNull
    private com.pngelectoral.ui.model.VotersList voterDetailsResponse;
    private final kotlin.Lazy scopedMediaPicker$delegate = null;
    private java.lang.String selectedImagePath;
    private java.util.HashMap _$_findViewCache;
    
    public Form11ImageCaptureActivity() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getLocalImagePath() {
        return null;
    }
    
    public final void setLocalImagePath(@org.jetbrains.annotations.Nullable
    java.lang.String p0) {
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
    
    private final com.pngelectoral.Form11ImageHelper.ScopedMediaPicker getScopedMediaPicker() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void captureForm11Image() {
    }
    
    private final void requestCameraPermission() {
    }
    
    private final void checkManageAllPermission() {
    }
    
    private final void captureImage() {
    }
    
    @java.lang.Override
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull
    int[] grantResults) {
    }
    
    @java.lang.Override
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable
    android.content.Intent data) {
    }
    
    private final void beginCrop(android.net.Uri source) {
    }
}