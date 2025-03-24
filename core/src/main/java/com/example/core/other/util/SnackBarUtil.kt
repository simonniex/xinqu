package com.example.core.other.util


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/*
* 用于控制SnackBar消息提示显示状态
* */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SnackbarUtil(
    snackbarHostState: SnackbarHostState,
    message: String,
    actionLabel: String? = null,
    duration: SnackbarDuration = SnackbarDuration.Short, // or any duration you prefer
    onAction: (() -> Unit)? = null,
    onDismiss: () -> Unit = {}, // Callback when Snackbar is dismissed
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch {
        val result = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = duration
        )
        if (result == SnackbarResult.ActionPerformed) {
            onAction?.invoke()
        }
        onDismiss() // Execute onDismiss when Snackbar is no longer visible
    }
}