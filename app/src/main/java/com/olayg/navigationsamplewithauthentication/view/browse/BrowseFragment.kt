package com.olayg.navigationsamplewithauthentication.view.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.olayg.navigationsamplewithauthentication.adapter.BrowseAdapter
import com.olayg.navigationsamplewithauthentication.databinding.FragmentBrowseBinding
import com.olayg.navigationsamplewithauthentication.model.Browse
import com.olayg.navigationsamplewithauthentication.util.DataState
import com.olayg.navigationsamplewithauthentication.util.showToast
import com.olayg.navigationsamplewithauthentication.view.MainActivity
import com.olayg.navigationsamplewithauthentication.viewmodel.BrowseViewModel

class BrowseFragment : Fragment() {

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    private val browseViewModel by viewModels<BrowseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentBrowseBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        root.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            (activity as? MainActivity)?.showFab = oldScrollY > scrollY
        }
    }

    private fun initObservers() = with(browseViewModel) {
        mockList.observe(viewLifecycleOwner) { dataState ->
            binding.browseProgress.isVisible = dataState is DataState.Loading
            if (dataState is DataState.Success) handleSuccess(dataState.item)
            if (dataState is DataState.Error) handleError(dataState.errorMsg)
        }
    }

    private fun handleSuccess(browseList: List<Browse>) = with(binding) {
        rvItems.adapter = BrowseAdapter(browseList, ::browseItemSelected)
    }

    private fun handleError(errorMsg: String) {
        showToast(errorMsg)
    }

    private fun browseItemSelected(browse: Browse) {
        showToast(browse.title)
    }
}