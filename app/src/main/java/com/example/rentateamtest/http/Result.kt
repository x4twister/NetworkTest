package com.example.rentateamtest.http

import com.example.rentateamtest.pojo.User
import com.google.gson.annotations.SerializedName

data class Result (
    @SerializedName("page")
    val page: Long,
    @SerializedName("per_page")
    val perPage: Long,
    @SerializedName("total")
    val total: Long,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("data")
    val users: List<User>
    )