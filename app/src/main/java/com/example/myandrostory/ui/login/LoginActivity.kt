package com.example.myandrostory.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myandrostory.R
import com.example.myandrostory.data.Result
import com.example.myandrostory.databinding.ActivityLoginBinding
import com.example.myandrostory.ui.ViewModelFactory
import com.example.myandrostory.ui.signup.SignUpActivity
import com.example.myandrostory.ui.story.StoryActivity
import com.example.myandrostory.utils.UserPreferences
import com.example.myandrostory.utils.dataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(application.dataStore)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@LoginActivity)
        val viewModel: LoginViewModel by viewModels {
            factory
        }

        binding.tvToDaftar.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            var valid = true
            val email = binding.edLoginEmail.text.toString().trim()
            val password = binding.edLoginPassword.text.toString().trim()

            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edLoginEmail.error = "Email tidak sesuai format"
                valid = false
            }
            if(password.length < 8) {
                binding.edLoginPassword.error = "Password minimal 8 karakter"
                valid = false
            }
            if(valid){
                viewModel.loginUser(email, password)
            }
        }

        viewModel.loginResult.observe(this) {result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val data = result.data
                    val token = data?.loginResult?.token
                    GlobalScope.launch {
                        if(token != null){
                            pref.saveUserToken(token)
                            pref.saveUserSession(true)
                        }
                    }
                    startActivity(Intent(this@LoginActivity, StoryActivity::class.java))
                    finish()
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this@LoginActivity,
                        getString(R.string.pastikan_email_dan_password_anda_benar), Toast.LENGTH_SHORT).show()
                }
                else -> showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.isClickable = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isClickable = true
        }
    }
}