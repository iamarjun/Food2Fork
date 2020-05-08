package com.arjun.food2fork

import android.app.Application
import com.arjun.food2fork.di.application.ApplicationComponent
import com.arjun.food2fork.di.application.ApplicationModule
import com.arjun.food2fork.di.application.DaggerApplicationComponent

class App : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        applicationComponent.inject(this)
        super.onCreate()

    }
}