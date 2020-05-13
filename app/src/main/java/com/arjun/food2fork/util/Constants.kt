package com.arjun.food2fork.util

import android.net.Uri
import com.arjun.food2fork.model.Recipe

object Constants {

    const val BASE_URL = "https://recipesapi.herokuapp.com/api/"

    private val DEFAULT_SEARCH_CATEGORIES = arrayOf(
        "Barbeque",
        "Breakfast",
        "Chicken",
        "Beef",
        "Brunch",
        "Dinner",
        "Wine",
        "Italian"
    )

    private val DEFAULT_SEARCH_CATEGORY_IMAGES = arrayOf(
        "barbeque",
        "breakfast",
        "chicken",
        "beef",
        "brunch",
        "dinner",
        "wine",
        "italian"
    )

    fun getCategoryList(): List<Recipe> {
        return DEFAULT_SEARCH_CATEGORIES.zip(DEFAULT_SEARCH_CATEGORY_IMAGES) { name, image ->
            Recipe(
                id = null,
                imageUrl = Uri.parse("android.resource://com.arjun.food2fork/drawable/${image}")
                    .toString(),
                ingredients = null,
                publisher = null,
                publisherUrl = null,
                recipeId = null,
                socialRank = null,
                sourceUrl = null,
                title = name,
                v = null
            )
        }
    }
}