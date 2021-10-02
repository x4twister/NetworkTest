package com.example.rentateamtest

import androidx.lifecycle.*
import com.example.rentateamtest.pojo.User
import com.example.rentateamtest.repository.UsersRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

//@HiltViewModel
class DetailViewModel @AssistedInject constructor (
    @Assisted savedStateHandle: SavedStateHandle,
    @Assisted private val userId: String,
    usersRepository: UsersRepository) : ViewModel() {

    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(handle: SavedStateHandle, userId: String): DetailViewModel
    }

    private val _user=MutableLiveData<User>()
    val user : LiveData<User> = _user

    init {
        viewModelScope.launch {
            _user.value=usersRepository.getUser(userId)
        }
    }
}

