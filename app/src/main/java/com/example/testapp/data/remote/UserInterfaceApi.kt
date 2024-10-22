package com.example.testapp.data.remote

import com.example.testapp.model.Todo
import retrofit2.Response
import retrofit2.http.GET


interface UserInterfaceApi {

    @GET("/posts")
    suspend fun getUserList(
    ): Response<List<Todo>>

}