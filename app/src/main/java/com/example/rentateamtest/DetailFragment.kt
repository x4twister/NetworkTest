package com.example.rentateamtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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

@AndroidEntryPoint
class DetailFragment : Fragment() {

    @Inject
    lateinit var detailViewModelFactory: DetailViewModel.DetailViewModelFactory

    private val viewModel: DetailViewModel by viewModels{
        ViewModelFactory(this) { stateHandle ->
            detailViewModelFactory.create(stateHandle,arguments?.getString("key", "912") ?: "912")
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

        viewModel.user.let { (status,result) ->
            status.observe(viewLifecycleOwner) {
                indeterminateBar.isVisible=it.equals(Status.LOADING)

                if (it== Status.ERROR)
                    showError("Network error")
            }

            result
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ value ->
                lifecycleScope.launchWhenStarted{
                    firstNameView.text=value.firstName
                    lastNameView.text=value.lastName
                    emailView.text=value.email

                    Picasso.get()
                        .load(value.avatar)
                        .fit()
                        .into(avatarView)
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
}
