package com.arjun.food2fork.repositories.inMemory

import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.repositories.Listing
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val restApi: RestApi) {

    fun recipeList(query: String): Listing<Recipe> {
        val factory =
            RecipeDataSourceFactory(
                query,
                restApi
            )

        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()
        val livePageListBuilder = LivePagedListBuilder(factory, config).build()

        val networkState = switchMap(factory.dataSource) { it.networkState }
        val refreshState = switchMap(factory.dataSource) { it.initialLoad }

        return Listing(
            pagedList = livePageListBuilder,
            networkState = networkState,
            retry = { factory.dataSource.value?.retryAllFailed() },
            refresh = { factory.dataSource.value?.invalidate() },
            refreshState = refreshState,
            clearCoroutineJobs = { factory.dataSource.value?.clearCoroutineJobs() }
        )

    }

}