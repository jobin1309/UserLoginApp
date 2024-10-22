package com.example.testapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflixclone.utils.NetworkResult
import com.example.testapp.R
import com.example.testapp.adapter.UserAdapter
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter



    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = userAdapter

        // Fetch data from ViewModel
        userViewModel.fetchdata()

        userViewModel.todoResponse.observe(this, Observer { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { users ->
                        userAdapter.updateUsers(users)
                    }
                }
                is NetworkResult.Error -> {
                    Log.e("Error", response.message ?: "Unknown error")
                }
                is NetworkResult.Loading -> {
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit_profile -> {
                // Start EditProfileActivity when the Edit button is clicked
                startActivity(Intent(this, EditActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
