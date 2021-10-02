package com.example.rentateamtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.rentateamtest.helpers.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.android.synthetic.main.detail_fragment.*

@AndroidEntryPoint
class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.user.observe(viewLifecycleOwner) {
            textView.text = viewModel.user.value?.id.toString()
        }
    }
}
