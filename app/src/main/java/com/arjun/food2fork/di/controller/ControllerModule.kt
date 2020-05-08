package com.arjun.food2fork.di.controller

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides

@Module
class ControllerModule(private val mActivity: FragmentActivity) {

    @Provides
    fun context(): Context = mActivity

    @Provides
    fun activity(): Activity = mActivity

}