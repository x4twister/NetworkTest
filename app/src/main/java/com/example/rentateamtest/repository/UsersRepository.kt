package com.example.rentateamtest.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rentateamtest.db.UserDao
import com.example.rentateamtest.http.WebService
import com.example.rentateamtest.pojo.User
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val webService: WebService,
    private val userDao: UserDao
) {
    fun getUsers(count: Long, pos: Long)= channelFlow<Either<List<User>>> {
        var status=Status.LOADING

        CoroutineScope(Dispatchers.Default).launch {
            try {
                // TODO count/pos -> page
                webService.getUsers(pos/6+1).let { result ->
                    status=Status.LOADED
                    // TODO delete/update?
                    result.users.forEach {
                        userDao.save(it)
                    }
                }
            } catch (exception: Throwable) {
                status=Status.NETWORK_ERROR
                exception.printStackTrace()
                send(Either(left=status))
            }
        }

        launch {
            userDao.load(count,pos)
                .catch { error ->
                    status=Status.APPLICATION_ERROR
                    error.printStackTrace()
                    send(Either(left=status))
                }
                .collect{ value ->
                    send(Either(value,status))
                }
        }
    }

    fun getUser(userId: String) = channelFlow<Either<User>> {
        var status=Status.LOADING

        CoroutineScope(Dispatchers.Default).launch {
            try {
                webService.getUser(userId).let { result ->
                    status=Status.LOADED
                    userDao.save(result.user)
                }
            } catch (exception: Throwable) {
                status=Status.NETWORK_ERROR
                exception.printStackTrace()
                send(Either(left=status))
            }
        }

        launch {
            userDao.loadById(userId.toLong())
                .catch { error ->
                    status=Status.APPLICATION_ERROR
                    error.printStackTrace()
                    send(Either(left=status))
                }
                .collect { value ->
                    send(Either(value,status))
                }
        }
    }
}