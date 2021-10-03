package com.example.rentateamtest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.rentateamtest.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val savedStateHandle: SavedStateHandle,
    usersRepository: UsersRepository
) : ViewModel() {

    // TODO pagination
    val users=usersRepository.getUsers(6,0)
}