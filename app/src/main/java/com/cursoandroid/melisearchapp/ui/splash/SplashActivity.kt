package com.cursoandroid.melisearchapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.cursoandroid.melisearchapp.R
import com.cursoandroid.melisearchapp.ui.home.HomeActivity
/*
 * Pantalla de inicio.
 */
class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000 // 1 seg

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this,HomeActivity::class.java))
            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}