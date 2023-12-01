package com.example.myandrostory.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myandrostory.databinding.ActivityWelcomeBinding
import com.example.myandrostory.ui.login.LoginActivity
import com.example.myandrostory.ui.signup.SignUpActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnToLogin.setOnClickListener {
                startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                finish()
            }
            btnToSignup.setOnClickListener {
                startActivity(Intent(this@WelcomeActivity, SignUpActivity::class.java))
                finish()
            }
        }

        playAnimation()
    }

    private fun playAnimation(){
        val welcomeAt = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(500)
        val androStory = ObjectAnimator.ofFloat(binding.tvAndroStory, View.ALPHA, 1f).setDuration(500)
        val button = ObjectAnimator.ofFloat(binding.llWelcome, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(welcomeAt, androStory, button)
            start()
        }
    }
}