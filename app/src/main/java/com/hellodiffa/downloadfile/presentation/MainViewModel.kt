package com.hellodiffa.downloadfile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellodiffa.downloadfile.data.source.FileRepositoryImpl
import com.hellodiffa.downloadfile.domain.executor.Dispatcher
import com.hellodiffa.downloadfile.remote.DummyData
import com.hellodiffa.downloadfile.remote.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.errors.IOException
import java.util.concurrent.TimeoutException

class MainViewModel(
    private val repository: FileRepositoryImpl,
    private val dispatcher: Dispatcher
) : ViewModel() {
    private val _file = MutableLiveData<Resource>()
    val file: LiveData<Resource> get() = _file

    private fun setResourceFile(resultState: Resource) {
        _file.postValue(resultState)
    }

    internal fun downloadFile(data: DummyData) {
        viewModelScope.launch(dispatcher.background()) {
            try {
                repository.downloadFile(data.file, data).collect {

                    withContext(dispatcher.main()) {
                        setResourceFile(it)
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