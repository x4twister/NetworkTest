package com.example.rentateamtest.http

import com.example.rentateamtest.pojo.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("users")
    fun getUsers(): Observable<UsersResult>

    @GET("users")
    fun getUser(@Query("id") userId: String): Observable<UserResult>
}