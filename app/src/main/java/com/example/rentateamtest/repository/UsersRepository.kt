package com.example.rentateamtest.repository

import com.example.rentateamtest.http.WebService
import com.example.rentateamtest.pojo.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor() {

    //private val webService: WebService = TODO()

    //fun getUsers() = webService.getUsers()
    fun getUser(userId: String): User {
        return User(userId.toLong(),"","","","")
    }
}