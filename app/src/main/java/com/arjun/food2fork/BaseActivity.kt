package com.arjun.food2fork

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.arjun.food2fork.databinding.ActivityBaseBinding
import com.arjun.food2fork.di.controller.ControllerComponent
import com.arjun.food2fork.di.controller.ControllerModule
import com.arjun.food2fork.util.viewBinding

abstract class BaseActivity(resID: Int) : AppCompatActivity(resID) {

    private val binding: ActivityBaseBinding by viewBinding(ActivityBaseBinding::inflate)
    private lateinit var progressBar: ProgressBar

    val controllerComponent: ControllerComponent by lazy {
        (application as App).applicationComponent
            .controllerComponent(ControllerModule(this))
    }

    override fun setContentView(layoutResID: Int) {
        layoutInflater.inflate(layoutResID, binding.activityContent, true)
        progressBar = binding.progressBar
        super.setContentView(layoutResID)
    }

    fun showProgressBar(boolean: Boolean) {
        progressBar.visibility = if (boolean) View.VISIBLE else View.INVISIBLE
    }
}