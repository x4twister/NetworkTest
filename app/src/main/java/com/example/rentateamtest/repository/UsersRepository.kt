package com.example.rentateamtest.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rentateamtest.db.UserDao
import com.example.rentateamtest.http.WebService
import com.example.rentateamtest.pojo.User
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val webService: WebService,
    private val userDao: UserDao
) {
    @SuppressLint("CheckResult")
    fun getUsers(count: Long, pos: Long)=Observable.create<Either<List<User>>>{ emitter ->
        var status=Status.LOADING

        // TODO count/pos -> page
        webService.getUsers(pos/6+1)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                status=Status.LOADED
                // TODO delete/update?
                result.users.forEach {
                    userDao.save(it)
                }
            }, { error ->
                status=Status.NETWORK_ERROR
                error.printStackTrace()
                emitter.onNext(Either(left=status))
            })

        userDao.load(count,pos)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe ({ value ->
                emitter.onNext(Either(value,status))
            }, { error ->
                status=Status.APPLICATION_ERROR
                error.printStackTrace()
                emitter.onNext(Either(left=status))
            })
    }

    @SuppressLint("CheckResult")
    fun getUser(userId: String)=Observable.create<Either<User>>{ emitter ->
        var status=Status.LOADING

        webService.getUser(userId)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                status=Status.LOADED
                userDao.save(result.user)
            }, { error ->
                status=Status.NETWORK_ERROR
                error.printStackTrace()
                emitter.onNext(Either(left=status))
            })

        userDao.loadById(userId.toLong())
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe ({ value ->
                emitter.onNext(Either(value,status))
            }, { error ->
                status=Status.APPLICATION_ERROR
                error.printStackTrace()
                emitter.onNext(Either(left=status))
            })
    }
}