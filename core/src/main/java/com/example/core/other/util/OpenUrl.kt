package com.example.core.other.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

fun openUrl(videoUrl: String,context:Context) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(videoUrl)
    }
    context.startActivity(intent) // 在合适的上下文中调用
}
