package com.arjun.food2fork.di.database

import android.app.Application
import androidx.room.Room
import com.arjun.food2fork.databse.RecipeDatabase
import com.arjun.food2fork.di.application.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    @ApplicationScope
    fun provideDatabase(application: Application): RecipeDatabase =
        Room.databaseBuilder(application, RecipeDatabase::class.java, RecipeDatabase.DATABASE_NAME)
            .addMigrations()
            .fallbackToDestructiveMigration()
            .build()
}