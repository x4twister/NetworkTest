package com.example.rentateamtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.rentateamtest.helpers.Modules
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject

//@HiltViewModel @Inject
class HomeViewModel constructor (private val savedStateHandle: SavedStateHandle) : ViewModel() {

    fun detail(view: View, value: Int){
        val args = Bundle().apply {
            putString("key", value.toString())
        }
        view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,args)
    }
}