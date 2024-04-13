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
import com.viswa.hobbyapp_160421069.databinding.FragmentSignUpBinding
import com.viswa.hobbyapp_160421069.model.User
import com.viswa.hobbyapp_160421069.viewmodel.UsersViewModel


class SignUpFragment : Fragment() {

    private  lateinit var binding:FragmentSignUpBinding
    private lateinit var viewModel: UsersViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding = FragmentSignUpBinding.inflate(inflater, container,false)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSignup=view.findViewById<Button>(R.id.btnRegister)

        btnSignup.setOnClickListener{
            val username = binding.txtUsernameR.text.toString()
            val password = binding.txtPasswordR.text.toString()
            val firstName = binding.txtFirstNameR.text.toString()
            val lastName = binding.txtLastNameR.text.toString()
            val photoUrl = binding.txtPhotoUrlR.text.toString()

            val user = User(0,username, password, firstName, lastName, photoUrl)
            if(password == binding.txtPasswordConfirmR.text.toString()){
                viewModel.registerUser(user, {
                    Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigateUp()
                }, { errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                })
            }
            else{
                Toast.makeText(requireContext(), "Password and the confirmation doesn't match", Toast.LENGTH_SHORT).show()
            }

        }
    }
}