package com.pngelectoral.utility.visionBarcode

import android.content.Context
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.Barcode

class BarcodeTrackerFactory(context: Context?) : MultiProcessor.Factory<Barcode> {
    private var mContext: Context? = context

    override fun create(barcode: Barcode?): BarcodeTracker {
        return BarcodeTracker(mContext)
    }
}