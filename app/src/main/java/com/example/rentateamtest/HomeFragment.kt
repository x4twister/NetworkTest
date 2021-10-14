package com.example.rentateamtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentateamtest.databinding.HomeFragmentBinding
import com.example.rentateamtest.databinding.HomeItemBinding
import com.example.rentateamtest.pojo.User
import com.example.rentateamtest.repository.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableReplay.observeOn
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private val homeFragmentAdapter=HomeFragmentAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.list_fragment, container, false)

        val binding: HomeFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.home_fragment,container,false)
        binding.viewModel=viewModel

        binding.recycleView.apply {
            layoutManager= LinearLayoutManager(activity)
            adapter=homeFragmentAdapter
        }

        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.users
                .catch { error ->
                    error.printStackTrace()
                }
                .collect { either ->
                    indeterminateBar.isVisible = either.left == Status.LOADING

                    either.right?.let {
                        homeFragmentAdapter.apply {
                            setUsers(it)
                            notifyDataSetChanged()
                        }
                    } ?: run {
                        showError("${either.left}")
                    }
            }
        }
    }

    private fun showError(text: String) {
        Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_SHORT).show()
    }

    inner class HomeFragmentHolder(private val binding: HomeItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(user: User) {
            binding.viewModel!!.user=user
            binding.executePendingBindings()
        }

        init {
            binding.viewModel=HomeItemViewModel(object: HomeItemViewModel.Callback{
                override fun onUserClick(id: Long) {
                    val args = Bundle().apply {
                        putString(DetailFragment.ARG_USER_ID, id.toString())
                    }
                    binding.root.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,args)
                }
            })
        }
    }

    inner class HomeFragmentAdapter(private var users: List<User>): RecyclerView.Adapter<HomeFragmentHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentHolder {
            val inflater=LayoutInflater.from(activity)
            val binding:HomeItemBinding=DataBindingUtil
                .inflate(inflater,R.layout.home_item,parent,false)

            return HomeFragmentHolder(binding)
        }

        override fun getItemCount()=users.size

        override fun onBindViewHolder(holder: HomeFragmentHolder, position: Int) {
            holder.bind(users[position])
        }

        fun setUsers(newUsers: List<User>){
            users=newUsers
        }
    }
}