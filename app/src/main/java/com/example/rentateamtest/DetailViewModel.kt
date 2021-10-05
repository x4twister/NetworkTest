package com.example.rentateamtest

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.rentateamtest.repository.UsersRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

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

