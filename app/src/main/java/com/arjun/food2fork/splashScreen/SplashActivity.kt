package com.arjun.food2fork.splashScreen

import android.content.Intent
import android.os.Bundle
import com.arjun.food2fork.MainActivity
import com.arjun.food2fork.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}