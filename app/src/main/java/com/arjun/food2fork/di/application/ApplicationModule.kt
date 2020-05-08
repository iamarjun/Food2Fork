package com.arjun.food2fork.di.application

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val app: Application) {

    @Provides
    @ApplicationScope
    fun application(): Application = app

}