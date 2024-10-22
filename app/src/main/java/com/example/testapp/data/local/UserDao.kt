package com.example.testapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.testapp.model.User


@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): User?

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}
