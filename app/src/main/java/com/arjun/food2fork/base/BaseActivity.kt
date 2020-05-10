package com.arjun.food2fork.base

import androidx.appcompat.app.AppCompatActivity
import com.arjun.food2fork.App
import com.arjun.food2fork.di.controller.ControllerComponent
import com.arjun.food2fork.di.controller.ControllerModule

abstract class BaseActivity() : AppCompatActivity() {

    val controllerComponent: ControllerComponent by lazy {
        (application as App).applicationComponent
            .controllerComponent(ControllerModule(this))
    }
}