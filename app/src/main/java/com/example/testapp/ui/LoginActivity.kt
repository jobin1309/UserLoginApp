package com.example.testapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.netflixclone.utils.NetworkResult
import com.example.testapp.databinding.ActivityLoginBinding
import com.example.testapp.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Login button click listener
        binding.btnLogin?.setOnClickListener {

            Log.d("ButtonClick", "True")
            // Retrieve the latest values from the EditTexts
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            // Check if fields are filled
            if (username.isNotEmpty() && password.isNotEmpty()) {
                userViewModel.login(username, password) { result ->
                    when (result) {
                        true -> {
                            // Handle successful login
                            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish() // Close LoginActivity
                        }

                        false -> {
                            Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Registration button click listener
        binding.registerButton?.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }
}
