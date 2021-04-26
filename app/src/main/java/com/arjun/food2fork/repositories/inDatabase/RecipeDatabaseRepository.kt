package com.arjun.food2fork.repositories.inDatabase

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.databse.RecipeDatabase
import com.arjun.food2fork.model.GetRecipe
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.util.NetworkBoundResource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class RecipeDatabaseRepository @Inject constructor(
    private val restApi: RestApi,
    private val db: RecipeDatabase
) {


    @OptIn(ExperimentalPagingApi::class)
    fun recipeList(query: String): Flow<PagingData<Recipe>> {

        val pagingSourceFactory = { db.recipeDao.getRecipeList(query) }

        Timber.d("New query: $query")
        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            remoteMediator = RecipeRemoteMediator(
                query,
                restApi,
                db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
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