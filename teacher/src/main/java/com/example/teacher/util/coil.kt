package com.example.teacher.util


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy

object CoilImageLoader {
    @Composable
    fun provideImageLoader(): ImageLoader {
        val context = LocalContext.current
        return remember {
            ImageLoader.Builder(context)
                .crossfade(true)
                .memoryCachePolicy(CachePolicy.ENABLED) // 启用内存缓存
                .diskCachePolicy(CachePolicy.ENABLED)   // 启用磁盘缓存
                .build()
        }
    }
}


@Composable
fun CachedImage(
    url: String,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    imageLoader: ImageLoader = CoilImageLoader.provideImageLoader()
) {
    Image(
        painter = rememberAsyncImagePainter(
            model = url,
            imageLoader = imageLoader
        ),
        contentDescription = contentDescription,
        modifier = modifier
    )
}
