package com.arjun.food2fork.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import com.arjun.food2fork.App
import com.arjun.food2fork.di.controller.ControllerComponent
import com.arjun.food2fork.di.controller.ControllerModule
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var imageLoader: ImageLoader


    val controllerComponent: ControllerComponent by lazy {
        (requireActivity().application as App).applicationComponent
            .controllerComponent(ControllerModule(requireActivity()))
    }
}