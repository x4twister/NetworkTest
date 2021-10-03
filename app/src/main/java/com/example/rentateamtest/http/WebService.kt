package com.example.rentateamtest.http

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("users")
    fun getUsers(@Query("page") page: Long): Observable<UsersResult>

    @GET("users")
    fun getUser(@Query("id") userId: String): Observable<UserResult>
}