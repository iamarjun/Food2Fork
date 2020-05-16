package com.arjun.food2fork.repositories.inDatabase

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.databse.RecipeDb
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.repositories.Listing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import java.util.concurrent.Executors
import javax.inject.Inject

class RecipeDatabaseRepository @Inject constructor(
    private val restApi: RestApi,
    private val db: RecipeDb
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
            coroutineScope = scope
        )

        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()

        val livePagedList = LivePagedListBuilder(db.recipeDao.getRecipeList("%$query%"), config)
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

}