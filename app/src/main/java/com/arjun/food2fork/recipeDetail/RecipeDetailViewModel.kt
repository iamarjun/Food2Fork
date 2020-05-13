package com.arjun.food2fork.recipeDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.arjun.food2fork.RestApi
import com.arjun.food2fork.base.BaseViewModel
import com.arjun.food2fork.model.GetRecipe
import com.arjun.food2fork.model.Recipe
import com.haroldadmin.cnradapter.NetworkResponse.*
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

class RecipeDetailViewModel @Inject constructor(private val restApi: RestApi) : BaseViewModel() {

    private val _recipeId by lazy { MutableLiveData<String>() }

    private val handler = CoroutineExceptionHandler { context, exception ->
        println("Caught $exception")
    }

    val recipe: LiveData<GetRecipe> = _recipeId.switchMap { recipeId ->
        liveData(handler) {
            val recipe = restApi.getRecipe(recipeId)

            when (recipe) {
                is Success -> emit(recipe.body)
                is ServerError -> println("Caught ${recipe.body}")
                is NetworkError -> println("Caught ${recipe.error}")
                is UnknownError -> println("Caught ${recipe.error}")
            }
        }
    }

    fun getRecipe(recipeId: String?) {
        _recipeId.value = recipeId
    }


}