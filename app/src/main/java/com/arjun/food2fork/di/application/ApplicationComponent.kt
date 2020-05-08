package com.arjun.food2fork.di.application

import android.app.Application
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(app: Application)
}