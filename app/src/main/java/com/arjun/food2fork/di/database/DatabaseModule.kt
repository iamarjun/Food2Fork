package com.arjun.food2fork.di.database

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arjun.food2fork.databse.RecipeDb
import com.arjun.food2fork.di.application.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    @ApplicationScope
    fun provideDatabase(application: Application): RecipeDb =
        Room.databaseBuilder(application, RecipeDb::class.java, RecipeDb.DATABASE_NAME)
            .addMigrations()
            .fallbackToDestructiveMigration()
            .build()
}