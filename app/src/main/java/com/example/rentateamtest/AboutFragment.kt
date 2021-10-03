package com.example.rentateamtest

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.rentateamtest.databinding.AboutFragmentBinding
import com.example.rentateamtest.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : Fragment() {

    private val viewModel: AboutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.about_fragment, container, false)

        val binding: AboutFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.about_fragment,container,false)
        binding.viewModel=viewModel
        return binding.root
    }
}