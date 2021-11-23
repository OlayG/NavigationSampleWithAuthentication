package com.olayg.navigationsamplewithauthentication.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.olayg.navigationsamplewithauthentication.databinding.FragmentLoginBinding
import com.olayg.navigationsamplewithauthentication.viewmodel.LoginViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLoginBinding.inflate(inflater, container, false).also {
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
        btnLogin.setOnClickListener {
            viewModel.login("username", "password")
            findNavController().navigate(LoginFragmentDirections.goToHomeGraph())
        }
        tvRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.goToDestinationRegister())
        }
    }
}