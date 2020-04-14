package com.hellodiffa.downloadfile

import android.app.Application
import com.hellodiffa.downloadfile.di.provideClient
import com.hellodiffa.downloadfile.di.provideDispatcher
import com.hellodiffa.downloadfile.di.provideRepository
import com.hellodiffa.downloadfile.di.provideViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DownloadFileApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DownloadFileApp)
            modules(
                provideClient, provideDispatcher, provideViewModel,
                provideRepository
            )
        }
    }
}