package com.arjun.food2fork.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arjun.food2fork.App
import com.arjun.food2fork.di.controller.ControllerComponent
import com.arjun.food2fork.di.controller.ControllerModule
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    val controllerComponent: ControllerComponent by lazy {
        (application as App).applicationComponent
            .controllerComponent(ControllerModule(this))
    }
}