package com.example.core.other.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.size.Scale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File

suspend fun Uri.toBitmap(context: Context): Bitmap? {
    return withContext(Dispatchers.IO) {
        // 创建一个 ImageLoader
        val imageLoader = ImageLoader(context)

        // 使用 ImageRequest 来加载并压缩图片
        val request = ImageRequest.Builder(context)
            .data(this@toBitmap)
            .size(300, 300) // 设置目标尺寸
            .scale(Scale.FIT)
            .allowHardware(false) // 避免使用硬件加速
            .build()

        // 执行请求并获取 Bitmap
        val result = imageLoader.execute(request)
        return@withContext (result.drawable as? BitmapDrawable)?.bitmap
    }
}

fun compressAndCacheAvatar(context: Context, avatarUri: Uri, userId: String) {
    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, avatarUri)
    val compressedBitmap = bitmap.compressToByteArray()
    val cacheFile = File(context.cacheDir, "${userId}_avatar.png") // 使用用户ID作为文件名
    cacheFile.writeBytes(compressedBitmap)
}


fun Bitmap.compressToByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 75, stream) // 75是压缩比例，可以调整
    return stream.toByteArray()
}