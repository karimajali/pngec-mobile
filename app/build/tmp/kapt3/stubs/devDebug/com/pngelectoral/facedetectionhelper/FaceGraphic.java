package com.pngelectoral.facedetectionhelper;

import java.lang.System;

/**
 * Graphic instance for rendering face position, contour, and landmarks within the associated
 * graphic overlay view.
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001a\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0001\u0010\u0015\u001a\u00020\u000fH\u0002R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\nR\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\nR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/pngelectoral/facedetectionhelper/FaceGraphic;", "Lcom/pngelectoral/facedetectionhelper/GraphicOverlay$Graphic;", "overlay", "Lcom/pngelectoral/facedetectionhelper/GraphicOverlay;", "face", "Lcom/google/mlkit/vision/face/Face;", "(Lcom/pngelectoral/facedetectionhelper/GraphicOverlay;Lcom/google/mlkit/vision/face/Face;)V", "boxPaints", "", "Landroid/graphics/Paint;", "[Landroid/graphics/Paint;", "facePositionPaint", "idPaints", "labelPaints", "numColors", "", "draw", "", "canvas", "Landroid/graphics/Canvas;", "drawFaceLandmark", "landmarkType", "Companion", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class FaceGraphic extends com.pngelectoral.facedetectionhelper.GraphicOverlay.Graphic {
    private final com.google.mlkit.vision.face.Face face = null;
    private final android.graphics.Paint facePositionPaint = null;
    private final int numColors = 0;
    private final android.graphics.Paint[] idPaints = null;
    private final android.graphics.Paint[] boxPaints = null;
    private final android.graphics.Paint[] labelPaints = null;
    @org.jetbrains.annotations.NotNull
    public static final com.pngelectoral.facedetectionhelper.FaceGraphic.Companion Companion = null;
    private static final float FACE_POSITION_RADIUS = 8.0F;
    private static final float ID_TEXT_SIZE = 30.0F;
    private static final float ID_Y_OFFSET = 40.0F;
    private static final float BOX_STROKE_WIDTH = 5.0F;
    private static final int NUM_COLORS = 10;
    private static final int[][] COLORS = {{-16777216, -1}, {-1, -65281}, {-16777216, -3355444}, {-1, -65536}, {-1, -16776961}, {-1, -12303292}, {-16777216, -16711681}, {-16777216, -256}, {-1, -16777216}, {-16777216, -16711936}};
    
    public FaceGraphic(@org.jetbrains.annotations.Nullable
    com.pngelectoral.facedetectionhelper.GraphicOverlay overlay, @org.jetbrains.annotations.NotNull
    com.google.mlkit.vision.face.Face face) {
        super(null);
    }
    
    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @java.lang.Override
    public void draw(@org.jetbrains.annotations.NotNull
    android.graphics.Canvas canvas) {
    }
    
    private final void drawFaceLandmark(android.graphics.Canvas canvas, @com.google.mlkit.vision.face.FaceLandmark.LandmarkType
    int landmarkType) {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/pngelectoral/facedetectionhelper/FaceGraphic$Companion;", "", "()V", "BOX_STROKE_WIDTH", "", "COLORS", "", "", "[[I", "FACE_POSITION_RADIUS", "ID_TEXT_SIZE", "ID_Y_OFFSET", "NUM_COLORS", "", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}