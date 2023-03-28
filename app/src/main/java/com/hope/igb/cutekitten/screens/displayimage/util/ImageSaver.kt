package com.hope.igb.cutekitten.screens.displayimage.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

class ImageSaver(private val context: Context, imageRes: Int) {



    private val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, imageRes)





    @Suppress("deprecation")
    fun appBaseFolder() : File{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            File(context.getExternalFilesDir(null)?.absolutePath, "Cute Kittens HD")
        else
            File(Environment.getExternalStorageDirectory(), "Cute Kittens HD")


    }




        fun saveImage(fileName : String): File{
            val imageFile = File(appBaseFolder(), "$fileName.jpg")
            val fot =FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fot)
            fot.flush()
            fot.close()

            return imageFile

        }



    fun delTempFile(){
        val tempFile = File(appBaseFolder(), "temp_image.jpg")
        if (tempFile.exists())
            tempFile.delete()
    }





}