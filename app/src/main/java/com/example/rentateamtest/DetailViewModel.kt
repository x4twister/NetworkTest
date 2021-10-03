package com.example.rentateamtest

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.example.rentateamtest.pojo.User
import com.example.rentateamtest.repository.UsersRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

//@HiltViewModel
@SuppressLint("CheckResult")
class DetailViewModel @AssistedInject constructor (
    @Assisted savedStateHandle: SavedStateHandle,
    @Assisted private val userId: String,
    usersRepository: UsersRepository) : ViewModel() {

    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(handle: SavedStateHandle, userId: String): DetailViewModel
    }

    val user=usersRepository.getUser(userId)
}

