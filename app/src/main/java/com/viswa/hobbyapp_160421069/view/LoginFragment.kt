package com.viswa.hobbyapp_160421069.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.viswa.hobbyapp_160421069.R
import com.viswa.hobbyapp_160421069.databinding.FragmentLoginBinding
import com.viswa.hobbyapp_160421069.viewmodel.UsersViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).getUserVM()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSignUp = view.findViewById<Button>(R.id.btnSignUp)
        val btnLogIn = view.findViewById<Button>(R.id.btnLogIn)

        btnSignUp.setOnClickListener {
            val action = LoginFragmentDirections.actionSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }

        btnLogIn.setOnClickListener {loginData ->
            val username = binding.txtUsernameL.text.toString()
            val password = binding.txtPasswordL.text.toString()

            viewModel.loginUser(username, password, {
                Toast.makeText(requireContext(), "Welcome " + username, Toast.LENGTH_SHORT).show()
                val action = LoginFragmentDirections.actionHomeFragment()
                Navigation.findNavController(loginData).navigate(action)
            }, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
    }
}