package com.arjun.food2fork

import androidx.fragment.app.Fragment
import com.arjun.food2fork.di.controller.ControllerComponent
import com.arjun.food2fork.di.controller.ControllerModule

abstract class BaseFragment : Fragment() {

    val controllerComponent: ControllerComponent by lazy {
        (requireActivity().application as App).applicationComponent
            .controllerComponent(ControllerModule(requireActivity()))
    }
}