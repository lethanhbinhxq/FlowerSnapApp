package com.example.flowerapp.ui.camera.capturepicture
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import java.io.OutputStream

@Factory
class SavePhoto(
    private val context: Context,
) {
    // Changed return type to Result<Uri?> to return the URI of the saved image
    suspend fun call(capturePhotoBitmap: Bitmap): Result<Uri?> = withContext(Dispatchers.IO) {
        val resolver: ContentResolver = context.applicationContext.contentResolver
        val imageCollection: Uri = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            else -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        // Publish a new image.
        val nowTimestamp: Long = System.currentTimeMillis()
        val imageContentValues: ContentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.DATE_TAKEN, nowTimestamp)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/FlowerApp")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                put(MediaStore.Images.Media.DATE_TAKEN, nowTimestamp)
                put(MediaStore.Images.Media.DATE_ADDED, nowTimestamp)
                put(MediaStore.Images.Media.DATE_MODIFIED, nowTimestamp)
                put(MediaStore.Images.Media.AUTHOR, "FlowerApp")
                put(MediaStore.Images.Media.DESCRIPTION, "Picture by FlowerApp")
            }
        }
        val imageMediaStoreUri: Uri? = resolver.insert(imageCollection, imageContentValues)
        // Write the image data to the new Uri.
        val result: Result<Uri?> = imageMediaStoreUri?.let { uri ->
            kotlin.runCatching {
                resolver.openOutputStream(uri).use { outputStream: OutputStream? ->
                    checkNotNull(outputStream) { "Couldn't create file for gallery, MediaStore output stream is null" }
                    capturePhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    imageContentValues.clear()
                    imageContentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(uri, imageContentValues, null, null)
                }
                Result.success(uri)  // Return the URI on success
            }.getOrElse { exception: Throwable ->
                exception.message?.let(::println)
                resolver.delete(uri, null, null)
                Result.failure(exception)
            }
        } ?: run {
            Result.failure(Exception("Couldn't create file for gallery"))
        }
        return@withContext result
    }
}