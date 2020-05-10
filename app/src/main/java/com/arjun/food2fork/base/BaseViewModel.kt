package com.arjun.food2fork.base

import androidx.lifecycle.ViewModel
import com.arjun.food2fork.RestApi

abstract class BaseViewModel(private val restApi: RestApi) : ViewModel() {

}