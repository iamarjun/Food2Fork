package com.arjun.food2fork.repositories.inDatabase

import androidx.paging.PagedList
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.databse.RecipeDatabase
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.util.PagingRequestHelper
import com.arjun.food2fork.util.createStatusLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class RecipeBoundaryCallback(
    private val query: String,
    ioExecutor: Executor,
    private val restApi: RestApi,
    private val db: RecipeDatabase,
    private val scope: CoroutineScope
) : PagedList.BoundaryCallback<Recipe>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    private var pageNumber = 1

    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            scope.launch(IO) {
                try {
                    val list = restApi.searchRecipe(query, pageNumber)
                    pageNumber++
                    db.recipeDao.insertRecipeList(list.recipes!!)
                    it.recordSuccess()

                } catch (e: Exception) {
                    it.recordFailure(e)
                }
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Recipe) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            scope.launch(IO) {

//                if (pageNumber != 0) {
//
//                } else
//                    it.recordSuccess()
                try {

                    val list = restApi.searchRecipe(query, pageNumber)
                    if (list.recipes.isNullOrEmpty()) {
//                        list.recipes?.forEach { it.pageNo.inc() }
                        db.recipeDao.insertRecipeList(list.recipes!!)
                    }
                    it.recordSuccess()

                } catch (e: Exception) {
                    it.recordFailure(e)
                }

            }
        }
    }

    private fun getPageNumber(query: String): Int {
        val count = db.recipeDao.getCount(query)
        return count / 30 + 1
    }

}