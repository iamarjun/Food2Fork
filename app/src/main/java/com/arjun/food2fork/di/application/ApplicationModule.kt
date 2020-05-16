package com.arjun.food2fork.di.application

import android.app.Application
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
class ApplicationModule(private val app: Application) {

    @Provides
    @ApplicationScope
    fun application(): Application = app

}