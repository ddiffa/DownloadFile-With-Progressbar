package com.hellodiffa.downloadfile.di

import com.hellodiffa.downloadfile.data.source.FileRepositoryImpl
import com.hellodiffa.downloadfile.domain.executor.Dispatcher
import com.hellodiffa.downloadfile.presentation.MainViewModel
import com.hellodiffa.downloadfile.remote.KtorBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val provideViewModel = module {
    viewModel { MainViewModel(get(), get()) }
}

val provideClient = module {
    single { KtorBuilder.provideKtorClient() }
}

val provideRepository = module {
    single { FileRepositoryImpl(get()) }
}

val provideDispatcher = module {
    single { Dispatcher.DEFAULT }
}