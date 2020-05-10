package com.arjun.food2fork.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.base.BaseViewModel
import com.arjun.food2fork.model.Recipe
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(private val restApi: RestApi) : BaseViewModel() {

    private val _recipeList by lazy { MutableLiveData<List<Recipe>>() }

    val recipeList: LiveData<List<Recipe>> = liveData {

    }

}