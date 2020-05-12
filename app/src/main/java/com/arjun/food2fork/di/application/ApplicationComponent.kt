package com.arjun.food2fork.di.application

import android.app.Application
import com.arjun.food2fork.di.controller.ControllerComponent
import com.arjun.food2fork.di.controller.ControllerModule
import com.arjun.food2fork.di.network.NetworkModule
import com.arjun.food2fork.di.viewModel.ViewModelModule
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class, NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun controllerComponent(controllerModule: ControllerModule): ControllerComponent

    fun inject(app: Application)
}