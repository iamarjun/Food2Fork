package com.arjun.food2fork.repositories.inDatabase

import androidx.paging.PagedList
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.databse.RecipeDb
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.util.PagingRequestHelper
import com.arjun.food2fork.util.createStatusLiveData
import com.haroldadmin.cnradapter.NetworkResponse.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Executor

class RecipeBoundaryCallback(
    private val query: String,
    ioExecutor: Executor,
    private val restApi: RestApi,
    private val db: RecipeDb,
    private val coroutineScope: CoroutineScope
) : PagedList.BoundaryCallback<Recipe>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            coroutineScope.launch(IO) {
                when (val list = restApi.searchRecipe(query, page)) {
                    is Success -> {
                        // Handle Success
                        db.recipeDao.insertRecipeList(list.body.recipes!!)
                        page.inc()
                        it.recordSuccess()
                    }
                    is ServerError -> {
                        Timber.d("Server Error ${list.body}")
                        it.recordFailure(Throwable())
                    }
                    is NetworkError -> {
                        Timber.d(list.error)
                        it.recordFailure(list.error)
                    }
                    is UnknownError -> {
                        Timber.d(list.error)
                        it.recordFailure(list.error)
                    }
                }
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Recipe) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            coroutineScope.launch(IO) {
                when (val list = restApi.searchRecipe(query, page)) {
                    is Success -> {
                        // Handle Success
                        db.recipeDao.insertRecipeList(list.body.recipes!!)
                        page.inc()
                    }
                    is ServerError -> {
                        Timber.d("Server Error ${list.body}")
                        it.recordFailure(Throwable())
                    }
                    is NetworkError -> {
                        Timber.d(list.error)
                        it.recordFailure(list.error)
                    }
                    is UnknownError -> {
                        Timber.d(list.error)
                        it.recordFailure(list.error)
                    }
                }
            }
        }
    }

    companion object {
        var page = 1

    }

}