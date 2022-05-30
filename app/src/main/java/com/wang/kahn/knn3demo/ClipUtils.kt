package com.wang.kahn.knn3demo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun copyToClipboard(context : Context, content: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("address", content)
    clipboard.setPrimaryClip(clip)
}