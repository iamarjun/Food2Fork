package com.arjun.food2fork.repositories.inDatabase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.databse.RecipeDatabase
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.model.RemoteKeys
import com.arjun.food2fork.repositories.inMemory.RecipePagingSource
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class RecipeRemoteMediator(
    private val query: String,
    private val restApi: RestApi,
    private val db: RecipeDatabase
) : RemoteMediator<Int, Recipe>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Recipe>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: PAGE
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }

        }
        val apiQuery = query

        try {
            val apiResponse = restApi.searchRecipe(apiQuery, page)

            val repos = apiResponse.recipes
            val endOfPaginationReached = repos.isEmpty()
            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao.clearRemoteKeys()
                    db.recipeDao.clearRecipe()
                }
                val prevKey = if (page == PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteKeysDao.insertAll(keys)
                db.recipeDao.insertRecipeList(repos)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Recipe>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { recipe ->
                // Get the remote keys of the last item retrieved
                db.remoteKeysDao.remoteKeysRepoId(recipe.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Recipe>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                db.remoteKeysDao.remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Recipe>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.remoteKeysDao.remoteKeysRepoId(repoId)
            }
        }
    }

    companion object {
        private const val PAGE = 1
    }
}