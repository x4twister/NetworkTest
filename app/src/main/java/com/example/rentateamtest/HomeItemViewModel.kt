package com.example.rentateamtest

import android.os.Bundle
import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.rentateamtest.pojo.User

class HomeItemViewModel : BaseObservable() {

    val value
        get() = "${user!!.firstName} ${user!!.lastName}"

    var user: User? = null
        set(value) {
            field = value
            notifyChange()
        }

    fun onClick(view: View){
        val args = Bundle().apply {
            // TODO
            putString("key", user!!.id.toString())
        }
        view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,args)
    }
}