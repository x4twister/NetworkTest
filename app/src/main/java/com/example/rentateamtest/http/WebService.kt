package com.example.rentateamtest.http

import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("users")
    suspend fun getUsers(@Query("page") page: Long): UsersResult

    @GET("users")
    suspend fun getUser(@Query("id") userId: String): UserResult
}