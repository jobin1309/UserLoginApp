package com.example.testapp.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityRegistrationBinding
import com.example.testapp.model.User
import com.example.testapp.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private var profilePictureUri: Uri? = null

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profilePic.setOnClickListener {
            chooseImageFromGalleryOrCamera()
        }
        binding.registerButton.setOnClickListener {
            registerUser()
        }
    }


    private fun registerUser() {
        val username = binding.usernameEditText.text.toString().trim()
        val fullName = binding.fullNameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()
        val dob = binding.dobEditText.text.toString().trim()

        // Basic validation
        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || dob.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

//        // Create user object
//        val newUser = User(username = username, fullName = fullName, password = password, dateOfBirth = dob, profilePicture = null)

        // Register user in the ViewModel
        userViewModel.registerUser(username, fullName, password, dob, null.toString())

        // Navigate to LoginActivity or HomeActivity after successful registration
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java) // or HomeActivity::class.java
        startActivity(intent)
        finish() // Close the Registration Activity
    }


    // Function to choose image from gallery or capture from camera
    private fun chooseImageFromGalleryOrCamera() {
        val options = arrayOf("Camera", "Gallery")
        AlertDialog.Builder(this)
            .setTitle("Choose Profile Picture")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }.show()
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_PICK -> {
                    data?.data?.let { uri ->
                        val savedImagePath = saveProfilePictureToStorage(uri)
                        if (savedImagePath != null) {
                            profilePictureUri = uri
                            binding.profilePic.setImageURI(uri)
                            Log.d("ImagePath", "Profile picture saved at: $savedImagePath")
                        } else {
                            Toast.makeText(this, "Failed to save image.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    // Save the captured image to temp storage and get the Uri
                    val tempUri = saveImageToTempStorage(imageBitmap)
                    val savedImagePath = saveProfilePictureToStorage(tempUri)
                    if (savedImagePath != null) {
                        profilePictureUri = tempUri
                        binding.profilePic.setImageURI(tempUri)
                        Log.d("ImagePath", "Profile picture saved at: $savedImagePath")
                    } else {
                        Toast.makeText(this, "Failed to save image.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    // Save image to app's internal storage
    private fun saveProfilePictureToStorage(uri: Uri?): String? {
        uri ?: return null
        val inputStream = contentResolver.openInputStream(uri)
        val file = File(filesDir, "profile_pictures/${System.currentTimeMillis()}.jpg")
        file.parentFile?.mkdirs()
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        outputStream.close()
        inputStream?.close()
        return file.absolutePath
    }

    // Save captured image from camera to temp storage
    private fun saveImageToTempStorage(bitmap: Bitmap): Uri {
        val file = File(filesDir, "temp_profile_picture.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return Uri.fromFile(file)
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
        private const val REQUEST_IMAGE_CAPTURE = 2
    }

}