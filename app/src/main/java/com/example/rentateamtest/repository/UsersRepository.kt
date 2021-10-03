package com.example.rentateamtest.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.rentateamtest.db.UserDao
import com.example.rentateamtest.http.WebService
import com.example.rentateamtest.pojo.User
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val webService: WebService,
    private val userDao: UserDao
) {

    fun getUser(userId: String): Flowable<User>{
        refreshUser(userId)

        return userDao.load(userId.toLong())
    }

    @SuppressLint("CheckResult")
    private fun refreshUser(userId: String) {
        webService.getUser(userId)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                Log.d("Result", "$result")
                userDao.save(result.user)
            }, { error ->
                Log.d("Result", "FAIL!")
                error.printStackTrace()
            })
    }
}