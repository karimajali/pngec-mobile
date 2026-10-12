
import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 2, d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a.\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e\u001a.\u0010\u000f\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e\u001a.\u0010\u0010\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e\u001a&\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e\u001a0\u0010\u0013\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e\u001a&\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e\u001a\"\u0010\u001a\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u001b\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001c\u001a\u00020\u0005H\u0002\u001a\u000e\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u0001\u001aB\u0010\u001f\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00052\b\u0010#\u001a\u0004\u0018\u00010\u0017\u001a\u0016\u0010$\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\t\u001a&\u0010&\u001a\u00020\u00052\u0006\u0010\'\u001a\u00020\u00152\u0006\u0010(\u001a\u00020\t2\u0006\u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020\u0005\u001a&\u0010+\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010,\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u0017\u001a&\u0010-\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u0017\u001a\u001e\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t00*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017\u00a8\u00061"}, d2 = {"bitmapToFile", "Ljava/io/File;", "bitmap", "Landroid/graphics/Bitmap;", "dstPath", "", "calculateDstRect", "Landroid/graphics/Rect;", "srcWidth", "", "srcHeight", "dstWidth", "dstHeight", "scalingLogic", "LScalingLogic;", "calculateSampleSize", "calculateSrcRect", "createScaledBitmap", "unscaledBitmap", "decodeFile", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "decodeResource", "filePath", "getCameraPhotoOrientation", "imageUri", "imagePath", "getImageQualityPercent", "file", "getResizeImage", "rotationNeeded", "", "currentPhotoPath", "IMAGE_CAPTURE_URI", "getRotatedBitmap", "rotate", "getScaledImagePath", "mContext", "imageUploadMaxWidth", "imageUploadMaxHeight", "path", "rotateBitmap", "bit", "setSelectedImage", "orignalBitmap", "getImageHgtWdt", "Lkotlin/Pair;", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class ScalingUtilsKt {
    
    /**
     * Utility function for decoding an image resource. The decoded bitmap will
     * be optimized for further scaling to the requested destination dimensions
     * and scaling logic.
     *
     * @param dstWidth
     * Width of destination area
     * @param dstHeight
     * Height of destination area
     * @param scalingLogic
     * Logic to use to avoid image stretching
     * @return Decoded bitmap
     */
    @org.jetbrains.annotations.NotNull
    public static final android.graphics.Bitmap decodeResource(@org.jetbrains.annotations.NotNull
    java.lang.String filePath, int dstWidth, int dstHeight, @org.jetbrains.annotations.NotNull
    ScalingLogic scalingLogic) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public static final android.graphics.Bitmap decodeFile(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.net.Uri uri, int dstWidth, int dstHeight, @org.jetbrains.annotations.NotNull
    ScalingLogic scalingLogic) {
        return null;
    }
    
    /**
     * Utility function for creating a scaled version of an existing bitmap
     *
     * @param unscaledBitmap Bitmap to scale
     * @param dstWidth Wanted width of destination bitmap
     * @param dstHeight Wanted height of destination bitmap
     * @param scalingLogic Logic to use to avoid image stretching
     * @return New scaled bitmap object
     */
    @org.jetbrains.annotations.NotNull
    public static final android.graphics.Bitmap createScaledBitmap(@org.jetbrains.annotations.NotNull
    android.graphics.Bitmap unscaledBitmap, int dstWidth, int dstHeight, @org.jetbrains.annotations.NotNull
    ScalingLogic scalingLogic) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String getScaledImagePath(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, int imageUploadMaxWidth, int imageUploadMaxHeight, @org.jetbrains.annotations.NotNull
    java.lang.String path) {
        return null;
    }
    
    /**
     * to convert bitmap to file.
     */
    @org.jetbrains.annotations.Nullable
    public static final java.io.File bitmapToFile(@org.jetbrains.annotations.Nullable
    android.graphics.Bitmap bitmap, @org.jetbrains.annotations.NotNull
    java.lang.String dstPath) {
        return null;
    }
    
    /**
     * This method is used to resize image
     *
     * @param context
     * @param dstWidth
     * @param dstHeight
     * @param scalingLogic
     * @param rotationNeeded
     * @param currentPhotoPath
     * @return scaledBitmap
     */
    @org.jetbrains.annotations.Nullable
    public static final android.graphics.Bitmap getResizeImage(@org.jetbrains.annotations.NotNull
    android.content.Context context, int dstWidth, int dstHeight, @org.jetbrains.annotations.NotNull
    ScalingLogic scalingLogic, boolean rotationNeeded, @org.jetbrains.annotations.NotNull
    java.lang.String currentPhotoPath, @org.jetbrains.annotations.Nullable
    android.net.Uri IMAGE_CAPTURE_URI) {
        return null;
    }
    
    /**
     * This method is used get orientation of camera photo
     *
     * @param context
     * @param imageUri  This parameter is Uri type
     * @param imagePath This parameter is String type
     * @return rotate
     */
    private static final int getCameraPhotoOrientation(android.content.Context context, android.net.Uri imageUri, java.lang.String imagePath) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final android.graphics.Bitmap getRotatedBitmap(@org.jetbrains.annotations.NotNull
    android.graphics.Bitmap bitmap, int rotate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final android.graphics.Bitmap setSelectedImage(@org.jetbrains.annotations.NotNull
    android.graphics.Bitmap orignalBitmap, @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String imagePath, @org.jetbrains.annotations.NotNull
    android.net.Uri IMAGE_CAPTURE_URI) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final android.graphics.Bitmap rotateBitmap(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.graphics.Bitmap bit, @org.jetbrains.annotations.NotNull
    java.lang.String imagePath, @org.jetbrains.annotations.NotNull
    android.net.Uri IMAGE_CAPTURE_URI) {
        return null;
    }
    
    /**
     * Calculate optimal down-sampling factor given the dimensions of a source
     * image, the dimensions of a destination area and a scaling logic.
     *
     * @param srcWidth Width of source image
     * @param srcHeight Height of source image
     * @param dstWidth Width of destination area
     * @param dstHeight Height of destination area
     * @param scalingLogic Logic to use to avoid image stretching
     * @return Optimal down scaling sample size for decoding
     */
    public static final int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight, @org.jetbrains.annotations.NotNull
    ScalingLogic scalingLogic) {
        return 0;
    }
    
    /**
     * Calculates source rectangle for scaling bitmap
     *
     * @param srcWidth Width of source image
     * @param srcHeight Height of source image
     * @param dstWidth Width of destination area
     * @param dstHeight Height of destination area
     * @param scalingLogic Logic to use to avoid image stretching
     * @return Optimal source rectangle
     */
    @org.jetbrains.annotations.NotNull
    public static final android.graphics.Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, @org.jetbrains.annotations.NotNull
    ScalingLogic scalingLogic) {
        return null;
    }
    
    /**
     * Calculates destination rectangle for scaling bitmap
     *
     * @param srcWidth Width of source image
     * @param srcHeight Height of source image
     * @param dstWidth Width of destination area
     * @param dstHeight Height of destination area
     * @param scalingLogic Logic to use to avoid image stretching
     * @return Optimal destination rectangle
     */
    @org.jetbrains.annotations.NotNull
    public static final android.graphics.Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, @org.jetbrains.annotations.NotNull
    ScalingLogic scalingLogic) {
        return null;
    }
    
    public static final int getImageQualityPercent(@org.jetbrains.annotations.NotNull
    java.io.File file) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final kotlin.Pair<java.lang.Integer, java.lang.Integer> getImageHgtWdt(@org.jetbrains.annotations.NotNull
    android.content.Context $this$getImageHgtWdt, @org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
}