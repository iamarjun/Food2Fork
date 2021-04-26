package com.arjun.food2fork.recipeDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.arjun.food2fork.base.BaseViewModel
import com.arjun.food2fork.repositories.inDatabase.RecipeDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(private val repo: RecipeDatabaseRepository) :
    BaseViewModel() {

    private val _recipeId by lazy { MutableLiveData<String>() }

    private val handler = CoroutineExceptionHandler { context, exception ->
        println("Caught $exception")
    }

    //    val recipe: LiveData<GetRecipe> = _recipeId.switchMap { recipeId ->
//        liveData(handler) {
//            val recipe = restApi.getRecipe(recipeId)
//
//            when (recipe) {
//                is Success -> emit(recipe.body)
//                is ServerError -> println("Caught ${recipe.body}")
//                is NetworkError -> println("Caught ${recipe.error}")
//                is UnknownError -> println("Caught ${recipe.error}")
//            }
//        }
//    }
//
    val recipe = _recipeId.switchMap { recipeId ->
        repo.getRecipe(recipeId)
    }

    fun getRecipe(recipeId: String?) {
        _recipeId.value = recipeId
    }


}