package com.example.rentateamtest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rentateamtest.helpers.ViewModelFactory
import com.example.rentateamtest.repository.Status
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.detail_fragment.indeterminateBar

@AndroidEntryPoint
class DetailFragment : Fragment() {

    @Inject
    lateinit var detailViewModelFactory: DetailViewModel.DetailViewModelFactory

    private val viewModel: DetailViewModel by viewModels{
        ViewModelFactory(this) { stateHandle ->
            detailViewModelFactory.create(stateHandle,arguments?.getString(ARG_USER_ID, "912") ?: "912")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ either ->
                lifecycleScope.launchWhenStarted{
                    indeterminateBar.isVisible= either.left == Status.LOADING

                    either.right?.let {
                        firstNameView.text=it.firstName
                        lastNameView.text=it.lastName
                        emailView.text=it.email

                        Picasso.get()
                            .load(it.avatar)
                            .fit()
                            .into(avatarView)
                    }?: run {
                        showError("${either.left}")
                    }
                }
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun showError(text: String) {
        Snackbar.make(detailCoordinatorLayout, text, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        const val ARG_USER_ID = "user_id"
    }
}
