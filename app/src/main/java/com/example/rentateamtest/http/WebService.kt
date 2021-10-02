package com.example.rentateamtest.http

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WebService {

    /*@GET("api/users")
    fun search(@Query("q") query: String,
               @Query("page") page: Int,
               @Query("per_page") perPage: Int): Observable<Result>*/

    @GET("api/users")
    fun getUsers(): Observable<Result>

    companion object Factory {
        fun create(): WebService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://reqres.in/")
                .build()

            return retrofit.create(WebService::class.java);
        }
    }
}