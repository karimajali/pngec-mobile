package com.pngelectoral.facedetectionhelper;

import java.lang.System;

/**
 * Face Detector Demo.
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u001f2\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001:\u0001\u001fB\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J\u0014\u0010\u0010\u001a\u00020\u00112\n\u0010\u0012\u001a\u00060\u0013j\u0002`\u0014H\u0014J\u001e\u0010\u0015\u001a\u00020\u00112\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\u001c\u0010\u0019\u001a\u00020\u00112\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u001c\u0010\u001c\u001a\u00020\u00112\b\u0010\u000e\u001a\u0004\u0018\u00010\u001d2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u001e\u001a\u00020\u0011H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/pngelectoral/facedetectionhelper/FaceDetectorProcessor;", "Lcom/pngelectoral/facedetectionhelper/VisionProcessorBase;", "", "Lcom/google/mlkit/vision/face/Face;", "context", "Landroid/content/Context;", "detectorOptions", "Lcom/google/mlkit/vision/face/FaceDetectorOptions;", "(Landroid/content/Context;Lcom/google/mlkit/vision/face/FaceDetectorOptions;)V", "detector", "Lcom/google/mlkit/vision/face/FaceDetector;", "mContext", "detectInImage", "Lcom/google/android/gms/tasks/Task;", "image", "Lcom/google/mlkit/vision/common/InputImage;", "onFailure", "", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onSuccess", "results", "graphicOverlay", "Lcom/pngelectoral/facedetectionhelper/GraphicOverlay;", "processBitmap", "bitmap", "Landroid/graphics/Bitmap;", "processImageProxy", "Landroidx/camera/core/ImageProxy;", "stop", "Companion", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class FaceDetectorProcessor extends com.pngelectoral.facedetectionhelper.VisionProcessorBase<java.util.List<? extends com.google.mlkit.vision.face.Face>> {
    private final com.google.mlkit.vision.face.FaceDetector detector = null;
    private final android.content.Context mContext = null;
    @org.jetbrains.annotations.NotNull
    public static final com.pngelectoral.facedetectionhelper.FaceDetectorProcessor.Companion Companion = null;
    private static final java.lang.String TAG = "FaceDetectorProcessor";
    
    public FaceDetectorProcessor(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    com.google.mlkit.vision.face.FaceDetectorOptions detectorOptions) {
        super(null);
    }
    
    @java.lang.Override
    public void stop() {
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    protected com.google.android.gms.tasks.Task<java.util.List<com.google.mlkit.vision.face.Face>> detectInImage(@org.jetbrains.annotations.NotNull
    com.google.mlkit.vision.common.InputImage image) {
        return null;
    }
    
    @java.lang.Override
    protected void onSuccess(@org.jetbrains.annotations.NotNull
    java.util.List<? extends com.google.mlkit.vision.face.Face> results, @org.jetbrains.annotations.NotNull
    com.pngelectoral.facedetectionhelper.GraphicOverlay graphicOverlay) {
    }
    
    @java.lang.Override
    protected void onFailure(@org.jetbrains.annotations.NotNull
    java.lang.Exception e) {
    }
    
    @java.lang.Override
    public void processBitmap(@org.jetbrains.annotations.Nullable
    android.graphics.Bitmap bitmap, @org.jetbrains.annotations.Nullable
    com.pngelectoral.facedetectionhelper.GraphicOverlay graphicOverlay) {
    }
    
    @java.lang.Override
    public void processImageProxy(@org.jetbrains.annotations.Nullable
    androidx.camera.core.ImageProxy image, @org.jetbrains.annotations.Nullable
    com.pngelectoral.facedetectionhelper.GraphicOverlay graphicOverlay) {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/pngelectoral/facedetectionhelper/FaceDetectorProcessor$Companion;", "", "()V", "TAG", "", "logExtrasForTesting", "", "face", "Lcom/google/mlkit/vision/face/Face;", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        private final void logExtrasForTesting(com.google.mlkit.vision.face.Face face) {
        }
    }
}