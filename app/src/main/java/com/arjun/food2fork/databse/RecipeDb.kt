package com.arjun.food2fork.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arjun.food2fork.model.Recipe

@Database(
    entities = [Recipe::class],
    exportSchema = false,
    version = 3
)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDb : RoomDatabase() {
    abstract val recipeDao: RecipeDao

    companion object {
        const val DATABASE_NAME = "recipe-db"
    }
}