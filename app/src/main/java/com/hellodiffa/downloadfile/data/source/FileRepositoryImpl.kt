package com.hellodiffa.downloadfile.data.source

import com.hellodiffa.downloadfile.domain.FileRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class FileRepositoryImpl(private val client: HttpClient) : FileRepository {

    override suspend fun downloadFile(file: File, url: String): Flow<HttpResponse> =
        flow {
            val response = client.call {
                url(url)
                method = HttpMethod.Get
            }.response
            emit(response)
        }
}
