package com.arjun.food2fork.repositories.inMemory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.repositories.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecipeDataSource(private val query: String, private val restApi: RestApi) :
    PageKeyedDataSource<Int, Recipe>() {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)
    private var retry: (() -> Any)? = null

    private var _networkState = MutableLiveData<NetworkState>()
    private var _initialLoad = MutableLiveData<NetworkState>()

    val networkState: LiveData<NetworkState>
        get() = _networkState

    val initialLoad: LiveData<NetworkState>
        get() = _initialLoad

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Recipe>
    ) {
        coroutineScope.launch {
            _initialLoad.postValue(NetworkState.LOADING)
            val list = restApi.searchRecipe(query, PAGE)

            try {
                retry = null
                _initialLoad.postValue(NetworkState.LOADED)
                callback.onResult(list.recipes!!, null, 2)
            } catch (e: Exception) {
                retry = {
                    loadInitial(params, callback)
                }
                _initialLoad.postValue(
                    NetworkState.error(
                        e.localizedMessage
                    )
                )
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Recipe>) {
        coroutineScope.launch {
            _networkState.postValue(NetworkState.LOADING)
            try {
                val list = restApi.searchRecipe(query, params.key)
                retry = null
                _networkState.postValue(NetworkState.LOADED)
                callback.onResult(list.recipes!!, params.key.inc())
            } catch (e: Exception) {
                retry = {
                    loadAfter(params, callback)
                }
                _networkState.postValue(
                    NetworkState.error(
                        e.localizedMessage
                    )
                )
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Recipe>) {

    }

    fun clearCoroutineJobs() {
        completableJob.cancel()
    }

    companion object {

        private const val PAGE = 1
    }

}