package com.arjun.food2fork.repositories.inMemory

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.model.Recipe
import retrofit2.HttpException
import java.io.IOException

class RecipePagingSource(private val query: String, private val restApi: RestApi) :
    PagingSource<Int, Recipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val position = params.key ?: PAGE
        val apiQuery = query

        return try {

            val response = restApi.searchRecipe(apiQuery, position)
            val list = response.recipes

            LoadResult.Page(
                data = list,
                prevKey = if (position == PAGE) null else position - 1,
                nextKey = if (list.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}