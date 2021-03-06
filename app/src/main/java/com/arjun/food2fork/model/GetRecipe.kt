package com.arjun.food2fork.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetRecipe(
    @Json(name = "recipe")
    var recipe: Recipe
)