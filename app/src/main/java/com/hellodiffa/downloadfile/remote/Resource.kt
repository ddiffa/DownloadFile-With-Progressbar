package com.hellodiffa.downloadfile.remote

data class Resource(
    val status: Status,
    val data: DummyData?,
    val progress: Int?,
    val error: String?
) {

    companion object {
        fun success(data: DummyData?): Resource =
            Resource(status = Status.SUCCESS, error = null, progress = 0, data = data)

        fun error(error: String): Resource =
            Resource(status = Status.ERROR, error = error, progress = 0, data = null)

        fun loading(progress: Int?, data: DummyData?): Resource =
            Resource(status = Status.LOADING, error = null, progress = progress, data =data)
    }

    enum class Status {
        SUCCESS,
        LOADING,
        ERROR
    }
}