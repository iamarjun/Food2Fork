package com.arjun.food2fork.recipeList

import com.arjun.food2fork.model.network.NetworkRecipe

interface Interaction {
    fun onItemSelected(position: Int, item: NetworkRecipe)
}
