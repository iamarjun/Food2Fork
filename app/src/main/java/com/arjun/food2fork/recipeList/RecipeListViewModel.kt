package com.arjun.food2fork.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.PagedList
import com.arjun.food2fork.base.BaseViewModel
import com.arjun.food2fork.model.network.NetworkRecipe
import com.arjun.food2fork.repositories.Listing
import com.arjun.food2fork.repositories.NetworkState
import com.arjun.food2fork.repositories.inMemory.RecipeRepository
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(private val repo: RecipeRepository) :
    BaseViewModel() {

    private val query by lazy { MutableLiveData<String>() }
    private val listing: LiveData<Listing<NetworkRecipe>>

    val networkRecipeList: LiveData<PagedList<NetworkRecipe>> // the recipe data to be observed
    val networkState: LiveData<NetworkState>
    val refreshState: LiveData<NetworkState> //the network states

    init {
        listing = map(query) { repo.recipeList(it) }
        networkRecipeList = switchMap(listing) { it.pagedList }
        networkState = switchMap(listing) { it.networkState }
        refreshState = switchMap(listing) { it.refreshState }
    }

    fun searchRecipe(query: String) {
        this.query.value = query
    }


}