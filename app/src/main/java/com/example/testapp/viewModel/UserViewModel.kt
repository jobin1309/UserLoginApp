package com.example.testapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testapp.repository.UserRepo
import com.example.netflixclone.utils.NetworkResult
import com.example.testapp.data.local.UserDao
import com.example.testapp.model.Todo
import com.example.testapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    application: Application,
    private val userRepo: UserRepo,
) : AndroidViewModel(application) {

    val loading = MutableLiveData<Boolean>()

    private val _todoResponse: MutableLiveData<NetworkResult<List<Todo>>> = MutableLiveData()
    val todoResponse: LiveData<NetworkResult<List<Todo>>> = _todoResponse


    private val _loginStatus: MutableLiveData<NetworkResult<User?>> = MutableLiveData()
    val loginStatus: LiveData<NetworkResult<User?>> = _loginStatus

    private var _currentUser: User? = null
    val currentUser get() = _currentUser


    fun registerUser(username: String, fullName: String, password: String, dob: String, profilePicture: String) {
        viewModelScope.launch {
             userRepo.registerUser(username, fullName, password, dob, profilePicture)
        }
    }

    // Login function
    fun login(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            userRepo.login(username, password, callback)
        }
    }


    // Fetch user data from the repository
    fun fetchdata() {
        // Indicate that data loading has started
        loading.value = true

        viewModelScope.launch {
            try {
                // Fetch user data
                val response = userRepo.getUserResponse()

                // Handle response and update LiveData
                _todoResponse.value = handleResponse(response)
                _todoResponse.value?.data?.let { data ->
                    if (data.isNotEmpty()) {
                        Log.d("data", data.toString())
                    }
                }
            } catch (e: Exception) {
                _todoResponse.value = NetworkResult.Error("Failed to fetch data: ${e.message}")
            } finally {
                // Indicate that data loading has completed
                loading.value = false
            }
        }
    }


    fun updateUserProfile(user: User, onUpdate: (Boolean) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepo.updateUser(user)
            }
            onUpdate(true) // Notify success
        }
    }

    // Function to handle the response
    private fun handleResponse(response: Response<List<Todo>>): NetworkResult<List<Todo>> {
        return if (response.isSuccessful && response.body() != null) {
            // Successful response
            NetworkResult.Success(response.body()!!)
        } else {
            // Error response
            NetworkResult.Error("Error: ${response.code()}")
        }
    }
}
