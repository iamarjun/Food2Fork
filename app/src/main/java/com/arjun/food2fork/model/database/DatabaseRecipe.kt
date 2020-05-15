package com.arjun.food2fork.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arjun.food2fork.model.domain.Recipe

@Entity
data class DatabaseRecipe(
    @PrimaryKey
    val id: String,
    val name: String,
    val publisher: String,
    val url: String,
    val socialRank: Float
)

fun List<DatabaseRecipe>.asDomainModel(): List<Recipe> {
    return map {
        Recipe(
            title = it.name,
            publisher = it.publisher,
            imageUrl = it.url,
            socialRank = it.socialRank
        )
    }
}