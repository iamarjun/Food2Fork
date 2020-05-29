package com.arjun.food2fork.util

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers.IO

/**
 * @param RequestType - from network
 * @param ResultType - from db
 */
abstract class NetworkBoundResource<ResultType, RequestType> {

    fun asLiveData() = liveData<Resource<ResultType>>(IO) {
        emit(Resource.Loading(null))
        val disposable = emitSource(loadFromDbLiveData().map { Resource.Loading(it) })

        val dbValue = loadFromDb()
        if (shouldFetch(dbValue)) {
            try {
                val apiResponse = fetchFromNetwork()
                disposable.dispose()
                saveNetworkResult(apiResponse)
                emitSource(loadFromDbLiveData().map { Resource.Success(it) })
            } catch (e: Exception) {
                emitSource(loadFromDbLiveData().map {
                    Resource.Error(
                        e.message ?: "Unknown Error",
                        it
                    )
                })
            }
        } else {
            emitSource(loadFromDbLiveData().map { Resource.Success(it) })
        }
    }

    @WorkerThread
    protected open fun processResponse(response: RequestType) = response

    @WorkerThread
    protected abstract suspend fun saveNetworkResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType

    @MainThread
    protected abstract fun loadFromDbLiveData(): LiveData<ResultType>

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): RequestType
}