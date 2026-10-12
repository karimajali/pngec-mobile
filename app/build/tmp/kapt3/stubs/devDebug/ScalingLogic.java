
import java.lang.System;

/**
 * ScalingLogic defines how scaling should be carried out if source and
 * destination image has different aspect ratio.
 *
 * CROP: Scales the image the minimum amount while making sure that at least
 * one of the two dimensions fit inside the requested destination area.
 * Parts of the source image will be cropped to realize this.
 *
 * FIT: Scales the image the minimum amount while making sure both
 * dimensions fit inside the requested destination area. The resulting
 * destination dimensions might be adjusted to a smaller size than
 * requested.
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2 = {"LScalingLogic;", "", "(Ljava/lang/String;I)V", "CROP", "FIT", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public enum ScalingLogic {
    /*public static final*/ CROP /* = new CROP() */,
    /*public static final*/ FIT /* = new FIT() */;
    
    ScalingLogic() {
    }
}