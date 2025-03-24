package com.example.core.other.util

import android.content.Context
import android.os.Environment
import java.io.File

fun getFolderSize(file: File): Long {
    var size = 0L
    val fileList = file.listFiles()
    fileList?.forEach { f ->
        size += if (f.isDirectory) {
            getFolderSize(f)
        } else {
            f.length()
        }
    }
    return size
}

fun getTotalCacheSize(context: Context): String {
    var cacheSize = getFolderSize(context.cacheDir)
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        context.externalCacheDir?.let {
            cacheSize += getFolderSize(it)
        }
    }
    return getFormattedSize(cacheSize)
}

fun getFormattedSize(size: Long): String {
    val kb = size / 1024
    if (kb < 1) return "$size B"
    val mb = kb / 1024
    if (mb < 1) return "$kb KB"
    val gb = mb / 1024
    return if (gb < 1) "$mb MB" else "$gb GB"
}


fun clearCache(context: Context) {
    // 清除内部缓存
    deleteDir(context.cacheDir)

    // 清除外部缓存
    context.externalCacheDir?.let { deleteDir(it) }
}

private fun deleteDir(dir: File): Boolean {
    return if (dir.isDirectory) {
        dir.listFiles()?.all { deleteDir(it) } == true && dir.delete()
    } else {
        dir.delete()
    }
}
