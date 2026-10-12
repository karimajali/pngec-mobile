package com.pngelectoral.utility.visionBarcode;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0011B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0002\u0010\u0005J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\u0012"}, d2 = {"Lcom/pngelectoral/utility/visionBarcode/BarcodeTracker;", "Lcom/google/android/gms/vision/Tracker;", "Lcom/google/android/gms/vision/barcode/Barcode;", "listener", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mListener", "Lcom/pngelectoral/utility/visionBarcode/BarcodeTracker$BarcodeGraphicTrackerCallback;", "getMListener", "()Lcom/pngelectoral/utility/visionBarcode/BarcodeTracker$BarcodeGraphicTrackerCallback;", "setMListener", "(Lcom/pngelectoral/utility/visionBarcode/BarcodeTracker$BarcodeGraphicTrackerCallback;)V", "onNewItem", "", "id", "", "item", "BarcodeGraphicTrackerCallback", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class BarcodeTracker extends com.google.android.gms.vision.Tracker<com.google.android.gms.vision.barcode.Barcode> {
    @org.jetbrains.annotations.Nullable
    private com.pngelectoral.utility.visionBarcode.BarcodeTracker.BarcodeGraphicTrackerCallback mListener;
    
    public BarcodeTracker(@org.jetbrains.annotations.Nullable
    android.content.Context listener) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.pngelectoral.utility.visionBarcode.BarcodeTracker.BarcodeGraphicTrackerCallback getMListener() {
        return null;
    }
    
    public final void setMListener(@org.jetbrains.annotations.Nullable
    com.pngelectoral.utility.visionBarcode.BarcodeTracker.BarcodeGraphicTrackerCallback p0) {
    }
    
    @java.lang.Override
    public void onNewItem(int id, @org.jetbrains.annotations.NotNull
    com.google.android.gms.vision.barcode.Barcode item) {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/pngelectoral/utility/visionBarcode/BarcodeTracker$BarcodeGraphicTrackerCallback;", "", "onDetectedQrCode", "", "barcode", "Lcom/google/android/gms/vision/barcode/Barcode;", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
    public static abstract interface BarcodeGraphicTrackerCallback {
        
        public abstract void onDetectedQrCode(@org.jetbrains.annotations.Nullable
        com.google.android.gms.vision.barcode.Barcode barcode);
    }
}