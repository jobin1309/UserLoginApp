package com.example.testapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.netflixclone.utils.NetworkResult
import com.example.testapp.data.local.UserDao
import com.example.testapp.data.remote.UserInterfaceApi
import com.example.testapp.model.Todo
import com.example.testapp.model.User
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor(
    private val userInterfaceApi: UserInterfaceApi,
    private val userDao: UserDao
) {

    // LiveData for registration state
    private val _registrationStatus: MutableLiveData<NetworkResult<User>> = MutableLiveData()
    val registrationStatus: LiveData<NetworkResult<User>> = _registrationStatus

    // REMOTE
    suspend fun getUserResponse(): Response<List<Todo>> {
        return userInterfaceApi.getUserList()
    }

    // LOCAL

    // Get a user by username and password for login
    suspend fun login(username: String, password: String, callback: (Boolean) -> Unit) {
      try {
            val user = userDao.login(username, password) // Your login logic
            if (user != null) {
                callback(true) // Successful login
            } else {
                callback(false) // Invalid credentials
            }
        } catch (e: Exception) {
            callback(false) // Handle exception, return false for login failure
        }
    }

    suspend fun registerUser(username: String, password: String, fullName: String, dob : String ,profilePicture: String) {
        try {
            // Create a new User object
            val newUser = User(username = username, fullName = fullName, password = password, dateOfBirth = dob, profilePicture = profilePicture)

            // Insert user into the database
            userDao.insert(newUser)

            // Update registration status
            _registrationStatus.value = NetworkResult.Success(newUser)
        } catch (e: Exception) {
            _registrationStatus.value = NetworkResult.Error("Registration failed: ${e.message}")
        }
    }


    // Update user details
    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

}