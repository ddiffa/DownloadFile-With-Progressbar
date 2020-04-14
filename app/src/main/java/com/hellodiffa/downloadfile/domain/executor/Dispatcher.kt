package com.hellodiffa.downloadfile.domain.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

interface Dispatcher {

    fun background(): CoroutineDispatcher
    fun main(): CoroutineDispatcher

    companion object {

        val DEFAULT: Dispatcher = object : Dispatcher {
            override fun background(): CoroutineDispatcher = IO
            override fun main(): CoroutineDispatcher = Main
        }
    }
}