package com.arjun.food2fork.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arjun.food2fork.model.network.NetworkRecipe

@Database(
    entities = [NetworkRecipe::class],
    exportSchema = false,
    version = 1
)
abstract class RecipeDb: RoomDatabase() {
    abstract val recipeDao: RecipeDao
    companion object {
        const val DATABASE_NAME = "recipe-db"
    }
}