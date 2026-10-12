package com.pngelectoral.utility.visionBarcode

import android.content.Context
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.barcode.Barcode

class BarcodeTracker(listener: Context?) : Tracker<Barcode>() {
    var mListener: BarcodeGraphicTrackerCallback? = listener as BarcodeGraphicTrackerCallback?

    interface BarcodeGraphicTrackerCallback {
        fun onDetectedQrCode(barcode: Barcode?)
    }

    override fun onNewItem(id: Int, item: Barcode) {
        if (item.displayValue != null) {
            mListener?.onDetectedQrCode(item)
        }
    }
}