package com.arjun.food2fork.di.database

import android.app.Application
import androidx.room.Room
import com.arjun.food2fork.databse.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabase(application: Application): RecipeDatabase =
        Room.databaseBuilder(application, RecipeDatabase::class.java, RecipeDatabase.DATABASE_NAME)
            .addMigrations()
            .fallbackToDestructiveMigration()
            .build()
}