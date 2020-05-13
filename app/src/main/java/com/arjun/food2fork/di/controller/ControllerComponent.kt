package com.arjun.food2fork.di.controller

import com.arjun.food2fork.recipeDetail.RecipeDetailFragment
import com.arjun.food2fork.recipeList.RecipeListFragment
import dagger.Subcomponent

@Subcomponent(modules = [ControllerModule::class])
interface ControllerComponent {
    fun inject(recipeListFragment: RecipeListFragment)
    fun inject(recipeListFragment: RecipeDetailFragment)

}