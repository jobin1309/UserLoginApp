package com.example.testapp.ui

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityEditBinding
import com.example.testapp.model.User
import com.example.testapp.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private val viewModel: UserViewModel by viewModels()
    private var profilePictureUri: Uri? = null
    private var currentUserId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

}



