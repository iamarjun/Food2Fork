package com.arjun.food2fork.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.PagedList
import com.arjun.food2fork.base.BaseViewModel
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.repositories.Listing
import com.arjun.food2fork.repositories.NetworkState
import com.arjun.food2fork.repositories.inDatabase.RecipeDatabaseRepository
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(private val repo: RecipeDatabaseRepository) :
    BaseViewModel() {

    private val query by lazy { MutableLiveData<String>() }
    private val listing: LiveData<Listing<Recipe>>

    val recipeList: LiveData<PagedList<Recipe>> // the recipe data to be observed
    val networkState: LiveData<NetworkState>
    val refreshState: LiveData<NetworkState> //the network states

    init {
        listing = map(query) { repo.getRecipeList(it) }
        recipeList = switchMap(listing) { it.pagedList }
        networkState = switchMap(listing) { it.networkState }
        refreshState = switchMap(listing) { it.refreshState }
    }

    fun searchRecipe(query: String) {
        this.query.value = query
    }
}