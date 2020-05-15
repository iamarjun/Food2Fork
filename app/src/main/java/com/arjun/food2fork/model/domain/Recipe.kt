package com.arjun.food2fork.model.domain

data class Recipe(
    var imageUrl: String,
    var publisher: String,
    var socialRank: Float,
    var title: String
)