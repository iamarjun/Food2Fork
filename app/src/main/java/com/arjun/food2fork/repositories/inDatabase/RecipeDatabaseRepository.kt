package com.arjun.food2fork.repositories.inDatabase

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.databse.RecipeDatabase
import com.arjun.food2fork.model.GetRecipe
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.repositories.Listing
import com.arjun.food2fork.util.NetworkBoundResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import java.util.concurrent.Executors
import javax.inject.Inject

class RecipeDatabaseRepository @Inject constructor(
    private val restApi: RestApi,
    private val db: RecipeDatabase
) {

    private val ioExecutor = Executors.newSingleThreadExecutor()
    private val job: Job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun getRecipeList(query: String): Listing<Recipe> {

        val bc = RecipeBoundaryCallback(
            query,
            ioExecutor = ioExecutor,
            restApi = restApi,
            db = db,
            scope = scope
        )

        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()

        val livePagedList =
            LivePagedListBuilder(db.recipeDao.getRecipeList("%${query.toLowerCase()}%"), config)
                .setBoundaryCallback(bc)
                .build()

        return Listing(
            pagedList = livePagedList,
            networkState = bc.networkState,
            retry = { bc.helper.retryAllFailed() },
            refreshState = bc.networkState,
            refresh = {},
            clearCoroutineJobs = { scope.cancel() }
        )
    }


    fun getRecipe(recipeId: String) = object : NetworkBoundResource<Recipe, GetRecipe>() {
        override suspend fun saveNetworkResult(item: GetRecipe) {
            db.recipeDao.updateRecipe(item.recipe)
        }

        override fun shouldFetch(data: Recipe?): Boolean = true

        override suspend fun loadFromDb(): Recipe = db.recipeDao.getRecipe(recipeId)

        override suspend fun fetchFromNetwork(): GetRecipe = restApi.getRecipe(recipeId)

        override fun loadFromDbLiveData(): LiveData<Recipe> =
            db.recipeDao.getRecipeLiveData(recipeId)

    }.asLiveData()

}