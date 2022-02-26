package com.arjun.food2fork.recipeList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arjun.food2fork.base.BaseViewModel
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.repositories.inDatabase.RecipeDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo: RecipeDatabaseRepository
) :
    BaseViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Recipe>>? = null

    fun searchRecipe(queryString: String): Flow<PagingData<Recipe>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Recipe>> = repo.recipeList(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}