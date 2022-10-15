package com.fidilaundry.app.util

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.esotericsoftware.minlog.Log
import java.io.*


object ImageUtils {
    @Throws(IOException::class)
    fun checkImage(bitmap: Bitmap?, files: File): File? {
        val exifInterface = ExifInterface(files.absolutePath)
        val orientation: Int = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        var rotatedBitmap: Bitmap? = null
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateImage(bitmap!!, 90F)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateImage(bitmap!!, 180F)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateImage(bitmap!!, 270F)
            ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
            else -> rotatedBitmap = bitmap
        }

        val stream = FileOutputStream(files)
        rotatedBitmap?.compress(Bitmap.CompressFormat.JPEG, 85, stream)
        stream.flush()
        stream.close()
        return resizeFile(files.absoluteFile)
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    fun resizeFile(file: File): File? {
        return try {
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            var inputStream = FileInputStream(file)
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()
            val REQUIRED_SIZE = 50
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            return file
        } catch (e: Exception) {
            return null
        }
    }

    fun getRealPathFromURI(context: Context, uri: Uri?): String? {
        var cursor: Cursor? = null
        return try {
            val proj =
                arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(uri!!, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    fun resizeImgGallery(context: Context, fileGall: File, uri: Uri): File {
        val bytes = ByteArrayOutputStream()

        val stream: InputStream? = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(stream)

        val wallpaperDirectory =
            File(Environment.getExternalStorageDirectory(), File.separator + "MetalGo")
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        var name = "Photo-Profile.jpg"
        val f = File(wallpaperDirectory, name)
        f.createNewFile()
        val file = File(f.path)
        val fo = FileOutputStream(f)

        fo.write(bytes.toByteArray())
        fo.close()
        return checkImageGall(bitmap, file, fileGall)!!
    }

    fun checkImageGall(bitmap: Bitmap?, files: File, ori: File): File? {
        val exifInterface = ExifInterface(files.absolutePath)
        val orientation: Int = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        var rotatedBitmap: Bitmap? = null
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateImage(bitmap!!, 90F)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateImage(bitmap!!, 180F)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateImage(bitmap!!, 270F)
            ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
            else -> rotatedBitmap = bitmap
        }

        val stream = FileOutputStream(files)
        rotatedBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
        return saveBitmapToFile(files.absoluteFile, ori)
    }

    fun saveBitmapToFile(file: File, ori: File): File? {
        return try {
            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 75

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

//            file.createNewFile()
            val outputStream = FileOutputStream(file)
            Log.info("file length: " + ori.length())
            if ((ori.length() / 1024) < 5000) {
                selectedBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            } else {
                selectedBitmap?.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)
            }
            file
        } catch (e: java.lang.Exception) {
            null
        }
    }

    fun deleteFile(context: Context, file: File) {
        file.delete()
        if (file.exists()) {
            file.canonicalFile.delete()
            if (file.exists()) {
                context.deleteFile(file.name)
            }
        }
    }
}