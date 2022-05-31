package com.wang.kahn.knn3demo

import android.app.Activity
import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File


const val DIR_NAME = "Web3Telescope"
fun copyToClipboard(context : Context, content: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("address", content)
    clipboard.setPrimaryClip(clip)
}

fun downloadImage(imageUrl: String, context: Context) {
    val direct = File(
        Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            .absolutePath + "/" + DIR_NAME + "/"
    )
    if (!direct.exists()) {
        direct.mkdir()
    }

    val fileName = createName(imageUrl)

    var mimetype = "image/png"
    if (fileName.endsWith(".gif")) {
        mimetype = "image/gif"
    }

    val downloadUri: Uri = Uri.parse(imageUrl)
    val request = DownloadManager.Request(downloadUri)
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        .setAllowedOverRoaming(false)
        .setTitle(fileName)
        .setMimeType(mimetype)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(
            Environment.DIRECTORY_PICTURES,
            File.separator + DIR_NAME + File.separator + fileName
        )

    (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
}


fun createName(url: String): String {
    var name = url.substring(url.lastIndexOf('/') + 1, url.length)
    if (name.lastIndexOf('.') < 0) {
        name += ".png"
    }
    return "${System.currentTimeMillis()}" + name
}

fun shareImage(file: File, activity: Activity) {
    val fileUri = FileProvider.getUriForFile(activity, "com.wang.kahn.knn3demo.fileprovider", file)
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, fileUri)
        type = "image/jpeg"
    }
    activity.startActivity(Intent.createChooser(shareIntent, "Share To"))
}