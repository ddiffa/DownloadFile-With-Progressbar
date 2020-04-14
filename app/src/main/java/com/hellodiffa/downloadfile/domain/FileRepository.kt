package com.hellodiffa.downloadfile.domain

import io.ktor.client.response.HttpResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FileRepository {

    suspend fun downloadFile(file: File, url: String): Flow<HttpResponse>
}