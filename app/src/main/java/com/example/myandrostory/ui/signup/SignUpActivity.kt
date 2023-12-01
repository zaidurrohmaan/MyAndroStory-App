package com.example.myandrostory.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myandrostory.R
import com.example.myandrostory.data.Result
import com.example.myandrostory.databinding.ActivitySignUpBinding
import com.example.myandrostory.ui.ViewModelFactory
import com.example.myandrostory.ui.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@SignUpActivity)
        val viewModel: SignUpViewModel by viewModels {
            factory
        }

        binding.tvToMasuk.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        }

        binding.btnDaftar.setOnClickListener {
            var valid = true
            val name = binding.edRegisterName.text.toString().trim()
            val email = binding.edRegisterEmail.text.toString().trim()
            val password = binding.edRegisterPassword.text.toString().trim()

            if (name.isEmpty()) {
                binding.edRegisterName.error = getString(R.string.nama_tidak_boleh_kosong)
                valid = false
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edRegisterEmail.error = getString(R.string.email_tidak_sesuai_format)
                valid = false
            }
            if (password.length < 8) {
                binding.edRegisterPassword.error = getString(R.string.password_minimal_8_karakter)
                valid = false
            }

            if (valid) {
                viewModel.registerUser(name, email, password)
            }
        }

        viewModel.registerResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    Toast.makeText(
                        this@SignUpActivity,
                        getString(R.string.registrasi_berhasil), Toast.LENGTH_SHORT
                    ).show()
                }

                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(
                        this@SignUpActivity,
                        getString(R.string.email_is_already_taken), Toast.LENGTH_SHORT
                    ).show()
                }

                else -> showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnDaftar.isClickable = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnDaftar.isClickable = true
        }
    }
}