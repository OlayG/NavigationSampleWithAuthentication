package com.olayg.navigationsamplewithauthentication.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.olayg.navigationsamplewithauthentication.databinding.FragmentHomeBinding
import com.olayg.navigationsamplewithauthentication.view.MainActivity

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        root.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            (activity as? MainActivity)?.showFab = oldScrollY > scrollY
        }
        buttonFirst.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.goToDestinationAnother())
        }
    }
}