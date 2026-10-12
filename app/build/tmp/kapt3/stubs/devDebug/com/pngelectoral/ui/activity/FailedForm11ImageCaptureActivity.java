package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0011H\u0002J\"\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\u0012\u0010\u001b\u001a\u00020\u00112\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J-\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0 2\u0006\u0010!\u001a\u00020\"H\u0016\u00a2\u0006\u0002\u0010#R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/pngelectoral/ui/activity/FailedForm11ImageCaptureActivity;", "Lcom/pngelectoral/base/BaseActivity;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityForm11ImageCaptureBinding;", "form11ImageCaptureViewModel", "Lcom/pngelectoral/ui/viewModel/Form11ImageCaptureViewModel;", "localImagePath", "", "scopedMediaPicker", "Lcom/pngelectoral/Form11ImageHelper/ScopedMediaPicker;", "getScopedMediaPicker", "()Lcom/pngelectoral/Form11ImageHelper/ScopedMediaPicker;", "scopedMediaPicker$delegate", "Lkotlin/Lazy;", "selectedImagePath", "beginCrop", "", "source", "Landroid/net/Uri;", "captureForm11Image", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class FailedForm11ImageCaptureActivity extends com.pngelectoral.base.BaseActivity {
    private com.pngelectoral.databinding.ActivityForm11ImageCaptureBinding binding;
    private com.pngelectoral.ui.viewModel.Form11ImageCaptureViewModel form11ImageCaptureViewModel;
    private java.lang.String localImagePath;
    private final kotlin.Lazy scopedMediaPicker$delegate = null;
    private java.lang.String selectedImagePath;
    private java.util.HashMap _$_findViewCache;
    
    public FailedForm11ImageCaptureActivity() {
        super();
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