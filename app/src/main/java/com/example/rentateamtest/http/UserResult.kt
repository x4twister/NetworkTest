package com.example.rentateamtest.http

import com.example.rentateamtest.pojo.User
import com.google.gson.annotations.SerializedName

data class UserResult (
    @SerializedName("data")
    val user: User
)