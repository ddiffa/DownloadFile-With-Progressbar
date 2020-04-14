package com.hellodiffa.downloadfile.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.hellodiffa.downloadfile.remote.DummyData
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.KoinContextHandler.get
import java.io.File

val globalContext: Context?
    get() = get()._scopeRegistry._rootScope?.androidContext()

fun Activity.openFile(file: File) {
    Intent(Intent.ACTION_VIEW).apply {
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        addCategory(Intent.CATEGORY_DEFAULT)
        val uri = FileProvider.getUriForFile(
            this@openFile,
            BuildConfig.APPLICATION_ID + ".provider",
            file
        )
        val mimeType = getMimeType(file)
        mimeType?.let {
            setDataAndType(uri, it)
            startActivity(this)
        }

    }

}

fun getMimeType(file: File): String? {
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)
}

val data = arrayOf(
    DummyData(
        "1",
        "Dictionary1.pdf",
        "https://css4.pub/2015/icelandic/dictionary.pdf"
    ),
    DummyData(
        "2",
        "Dictionary2.pdf",
        "https://css4.pub/2015/icelandic/dictionary.pdf"
    ),
    DummyData(
        "3",
        "Dictionary3.pdf",
        "https://css4.pub/2015/icelandic/dictionary.pdf"
    ),
    DummyData(
        "4",
        "DryLab1.pdf",
        "https://css4.pub/2017/newsletter/drylab.pdf"
    ),
    DummyData(
        "5",
        "DryLab2.pdf",
        "https://css4.pub/2017/newsletter/drylab.pdf"
    ),
    DummyData(
        "6",
        "DryLab3.pdf",
        "https://css4.pub/2017/newsletter/drylab.pdf"
    )

)