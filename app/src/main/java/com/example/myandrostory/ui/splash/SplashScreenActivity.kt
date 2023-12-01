package com.example.myandrostory.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.myandrostory.R
import com.example.myandrostory.ui.story.StoryActivity
import com.example.myandrostory.ui.welcome.WelcomeActivity
import com.example.myandrostory.utils.UserPreferences
import com.example.myandrostory.utils.dataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private val duration: Long = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )

        val pref = UserPreferences.getInstance(application.dataStore)

        Handler(Looper.getMainLooper()).postDelayed({
            GlobalScope.launch {
                if (pref.getUserSession().first()) {
                    startActivity(Intent(this@SplashScreenActivity, StoryActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashScreenActivity, WelcomeActivity::class.java))
                    finish()
                }
            }
        }, duration)
    }
}