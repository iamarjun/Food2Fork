package com.arjun.food2fork.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipePref(
    @PrimaryKey
    val recipeName: String,
    val pageNo: Int
)