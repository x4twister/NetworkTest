package com.example.rentateamtest

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController

class HomeViewModel : ViewModel() {

    fun detail(view: View){
        view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
    }
}