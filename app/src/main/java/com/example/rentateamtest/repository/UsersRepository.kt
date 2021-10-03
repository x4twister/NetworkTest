package com.example.rentateamtest.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rentateamtest.db.UserDao
import com.example.rentateamtest.http.WebService
import com.example.rentateamtest.pojo.User
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val webService: WebService,
    private val userDao: UserDao
) {

    fun getUsers(count: Long, pos: Long): Pair<LiveData<Status>,Flowable<List<User>>> {
        val status=MutableLiveData(Status.LOADING)
        refreshUsers(status,count,pos)

        return Pair(status,userDao.load(count,pos))
    }

    fun getUser(userId: String): Pair<LiveData<Status>,Flowable<User>>{
        val status=MutableLiveData(Status.LOADING)
        refreshUser(status,userId)

        return Pair(status,userDao.loadById(userId.toLong()))
    }

    @SuppressLint("CheckResult")
    private fun refreshUser(status: MutableLiveData<Status>,userId: String) {
        webService.getUser(userId)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                status.postValue(Status.IDLE)
                userDao.save(result.user)
            }, { error ->
                status.postValue(Status.ERROR)
                error.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    private fun refreshUsers(status: MutableLiveData<Status>,count: Long, pos: Long) {
        // TODO count/pos -> page
        webService.getUsers(pos/6+1)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                status.postValue(Status.IDLE)
                // TODO delete/update?
                result.users.forEach {
                    userDao.save(it)
                }
            }, { error ->
                status.postValue(Status.ERROR)
                error.printStackTrace()
            })
    }
}