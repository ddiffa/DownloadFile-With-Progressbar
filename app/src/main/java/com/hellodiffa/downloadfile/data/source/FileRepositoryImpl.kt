package com.hellodiffa.downloadfile.data.source

import com.hellodiffa.downloadfile.domain.FileRepository
import com.hellodiffa.downloadfile.remote.DummyData
import com.hellodiffa.downloadfile.remote.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.contentLength
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import kotlin.math.roundToInt

class FileRepositoryImpl(private val client: HttpClient) : FileRepository {

    override suspend fun downloadFile(file: File, dummy: DummyData): Flow<Resource> =
        flow {

            val response = client.call {
                url(dummy.url)
                method = HttpMethod.Get
            }.response

            val data = ByteArray(response.contentLength()!!.toInt())

            var offset = 0

            do {
                val currentRead = response.content.readAvailable(data, offset, data.size)
                offset += currentRead
                val progress = (offset * 100F / data.size).roundToInt()
                emit(Resource.loading(progress, dummy))
            } while (currentRead > 0)

            response.close()
            if (response.status.isSuccess()) {
                file.writeBytes(data)
                emit(Resource.success(dummy))
            } else {
                emit(Resource.error("Fle not Downloaded"))
            }
        }
}