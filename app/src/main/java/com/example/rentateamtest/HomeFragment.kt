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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentateamtest.databinding.HomeFragmentBinding
import com.example.rentateamtest.databinding.HomeItemBinding
import com.example.rentateamtest.pojo.User
import com.example.rentateamtest.repository.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
    }

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

        viewModel.users.let { (status,result) ->
            status.observe(viewLifecycleOwner) {
                indeterminateBar.isVisible=it.equals(Status.LOADING)

                if (it==Status.ERROR)
                    showError("Network error")
            }

            result
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ value ->
                lifecycleScope.launchWhenStarted {
                    homeFragmentAdapter.apply {
                        setUsers(value)
                        notifyDataSetChanged()
                    }
                }
            }, { error ->
                error.printStackTrace()
                showError("Something Went Wrong")
            })
        }
    }

    private fun showError(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
    }

    inner class HomeFragmentHolder(private val binding: HomeItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(user: User) {
            binding.viewModel!!.user=user
        }

        init {
            binding.viewModel=HomeItemViewModel()
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