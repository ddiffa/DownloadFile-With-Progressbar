package com.hellodiffa.downloadfile.domain

import com.hellodiffa.downloadfile.remote.DummyData
import com.hellodiffa.downloadfile.remote.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FileRepository {

    suspend fun downloadFile(file: File, data: DummyData): Flow<Resource>
}