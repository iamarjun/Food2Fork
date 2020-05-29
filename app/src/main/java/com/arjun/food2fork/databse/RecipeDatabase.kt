package com.arjun.food2fork.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.model.RecipePref

@Database(
    entities = [Recipe::class, RecipePref::class],
    exportSchema = false,
    version = 4
)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao

    companion object {
        const val DATABASE_NAME = "recipe-db"
    }
}