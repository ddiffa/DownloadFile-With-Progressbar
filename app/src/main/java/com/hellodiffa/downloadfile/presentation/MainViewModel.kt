package com.hellodiffa.downloadfile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellodiffa.downloadfile.data.source.FileRepositoryImpl
import com.hellodiffa.downloadfile.domain.executor.Dispatcher
import com.hellodiffa.downloadfile.remote.DummyData
import com.hellodiffa.downloadfile.remote.Resource
import io.ktor.http.contentLength
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.errors.IOException
import java.util.concurrent.TimeoutException
import kotlin.math.roundToInt

class MainViewModel(
    private val repository: FileRepositoryImpl,
    private val dispatcher: Dispatcher
) : ViewModel() {

    private val _file = MutableLiveData<Resource>()
    val file: LiveData<Resource> get() = _file

    private fun setResourceFile(resultState: Resource) {
        _file.postValue(resultState)
    }

    internal fun downloadFile(dummy: DummyData) {
        viewModelScope.launch(dispatcher.background()) {
            try {
                repository.downloadFile(dummy.file, dummy.url).collect {
                    withContext(dispatcher.main()) {
                        val data = ByteArray(it.contentLength()!!.toInt())
                        var offset = 0
                        do {
                            val currentRead =
                                it.content.readAvailable(data, offset, data.size)
                            offset += currentRead
                            val progress = (offset * 100F / data.size).roundToInt()
                            setResourceFile(Resource.loading(progress, dummy))
                        } while (currentRead > 0)
                        it.close()
                        if (it.status.isSuccess()) {
                            dummy.file.writeBytes(data)
                            setResourceFile(Resource.success(dummy))
                        } else {
                            setResourceFile(Resource.error("Download Failed"))
                        }
                    }

                }

            } catch (e: Exception) {

                when (e) {
                    is IOException -> {
                        setResourceFile(Resource.error("No internet Connection"))
                    }
                    is TimeoutException -> {
                        setResourceFile(Resource.error("Request Timeout"))
                    }
                    else -> {
                        setResourceFile(Resource.error("Download Failed"))
                    }
                }
            }
        }
    }
}