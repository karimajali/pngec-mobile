package com.pngelectoral.utility.visionBarcode;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0002\u0010\u0005J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/pngelectoral/utility/visionBarcode/BarcodeTrackerFactory;", "Lcom/google/android/gms/vision/MultiProcessor$Factory;", "Lcom/google/android/gms/vision/barcode/Barcode;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mContext", "create", "Lcom/pngelectoral/utility/visionBarcode/BarcodeTracker;", "barcode", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class BarcodeTrackerFactory implements com.google.android.gms.vision.MultiProcessor.Factory<com.google.android.gms.vision.barcode.Barcode> {
    private android.content.Context mContext;
    
    public BarcodeTrackerFactory(@org.jetbrains.annotations.Nullable
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public com.pngelectoral.utility.visionBarcode.BarcodeTracker create(@org.jetbrains.annotations.Nullable
    com.google.android.gms.vision.barcode.Barcode barcode) {
        return null;
    }
}