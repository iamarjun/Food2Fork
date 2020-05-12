package com.arjun.food2fork.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    @Json(name = "_id")
    var id: String?,
    @Json(name = "image_url")
    var imageUrl: String?,
    @Json(name = "ingredients")
    var ingredients: List<String>?,
    @Json(name = "publisher")
    var publisher: String?,
    @Json(name = "publisher_url")
    var publisherUrl: String?,
    @Json(name = "recipe_id")
    var recipeId: String?,
    @Json(name = "social_rank")
    var socialRank: Float?,
    @Json(name = "source_url")
    var sourceUrl: String?,
    @Json(name = "title")
    var title: String?,
    @Json(name = "__v")
    var v: Int?
)