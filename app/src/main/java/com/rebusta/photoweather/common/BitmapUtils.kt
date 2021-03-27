package com.rebusta.photoweather.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object BitmapUtils {

    fun convertViewToBitmap(v: ConstraintLayout): Bitmap {
        val b = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.RGB_565)
        val c = Canvas(b)
        v.draw(c)
        return b
    }

    fun replaceOldWithNewPhoto(capturedImageBitmap: Bitmap, photoPath: String) {
        val newBitmap = capturedImageBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val newModifiedCapturedImage = File(photoPath)
        if (!newModifiedCapturedImage.exists()) {
            try {
                val wasCreated = newModifiedCapturedImage.createNewFile()
                if (!wasCreated) {
                    Log.e("saveNewPhoto", "Failed to create directory")
                }
            } catch (e: IOException) {
                Log.e("saveNewPhoto", e.message)
            }
        } else {
            var outputStream: FileOutputStream? = null
            try {
                outputStream = FileOutputStream(newModifiedCapturedImage)
                newBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            } catch (e: FileNotFoundException) {
                Log.e("saveNewPhoto", e.message)
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.flush()
                        outputStream.fd.sync()
                        outputStream.close()
                    } catch (e: IOException) {
                        Log.e("saveNewPhoto", e.message)
                    }
                }
            }
        }
    }

}