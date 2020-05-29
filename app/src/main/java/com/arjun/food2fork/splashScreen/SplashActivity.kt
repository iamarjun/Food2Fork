package com.arjun.food2fork.splashScreen

import android.animation.Animator
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import com.arjun.food2fork.MainActivity
import com.arjun.food2fork.base.BaseActivity
import com.arjun.food2fork.databinding.SplashScreenBinding
import com.arjun.food2fork.util.viewBinding
import timber.log.Timber

class SplashActivity : BaseActivity() {

    private val binding: SplashScreenBinding by viewBinding(SplashScreenBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.splashLogo.setAnimation("splash_dark.json")
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.splashLogo.setAnimation("splash_light.json")
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
            }
        }

        binding.splashLogo.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                Timber.d("onAnimationRepeat")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Timber.d("onAnimationEnd")
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {
                Timber.d("onAnimationCancel")
            }

            override fun onAnimationStart(animation: Animator?) {
                Timber.d("onAnimationCancel")
            }

        })

    }
}