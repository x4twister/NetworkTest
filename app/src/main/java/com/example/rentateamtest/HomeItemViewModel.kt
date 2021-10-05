package com.example.rentateamtest

import android.os.Bundle
import android.view.View
import androidx.databinding.BaseObservable
import androidx.navigation.findNavController
import com.example.rentateamtest.pojo.User

class HomeItemViewModel(private val callback: Callback) : BaseObservable() {

    interface Callback {
        fun onUserClick(id: Long)
    }

    val value
        get() = "${user!!.firstName} ${user!!.lastName}"

    var user: User? = null
        set(value) {
            field = value
            notifyChange()
        }

    fun onClick(view: View){
        callback.onUserClick(user!!.id)
    }
}