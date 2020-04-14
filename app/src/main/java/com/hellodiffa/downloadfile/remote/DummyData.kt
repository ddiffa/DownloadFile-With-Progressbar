package com.hellodiffa.downloadfile.remote

import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.hellodiffa.downloadfile.utils.globalContext
import java.io.File

class DummyData(val id: String, val title: String, val url: String) {
    var isDownloading: Boolean = false

    var progress = 0

    val uriFile: Uri
        get() = file.toUri()
    val file: File
        get() = File(globalContext?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), title)
}