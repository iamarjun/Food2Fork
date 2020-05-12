package com.arjun.food2fork.recipeList

import com.arjun.food2fork.model.Recipe

interface Interaction {
        fun onItemSelected(position: Int, item: Recipe)
    }
