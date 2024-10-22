package com.example.testapp.model


import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("body")
    val title: String,
    @SerializedName("title")
    val completed: Boolean,
)