package com.arjun.food2fork.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arjun.food2fork.model.Recipe
import com.arjun.food2fork.model.RemoteKeys

@Database(
    entities = [Recipe::class, RemoteKeys::class],
    exportSchema = false,
    version = 5
)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
    abstract val remoteKeysDao: RemoteKeysDao

    companion object {
        const val DATABASE_NAME = "recipe-db"
    }
}