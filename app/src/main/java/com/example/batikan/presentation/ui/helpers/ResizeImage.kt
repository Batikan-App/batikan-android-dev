package com.example.batikan.presentation.ui.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun resizeImageFile(imageFile: File): File {
    val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)

    var quality = 100
    var outputStream: ByteArrayOutputStream
    do {
        outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        quality -= 5
    } while (outputStream.size() > 1_000_000 && quality > 0)

    // Simpan bitmap yang sudah dikompresi ke file baru
    val resizedFile = File(imageFile.parent, "resized_${imageFile.name}")
    FileOutputStream(resizedFile).use {
        it.write(outputStream.toByteArray())
    }

    return resizedFile
}
