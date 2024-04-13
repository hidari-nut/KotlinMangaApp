package com.viswa.hobbyapp_160421069.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso
import com.viswa.hobbyapp_160421069.R
import com.viswa.hobbyapp_160421069.databinding.FragmentSettingBinding
import com.viswa.hobbyapp_160421069.model.User
import com.viswa.hobbyapp_160421069.viewmodel.UsersViewModel


class SettingFragment : Fragment() {
    private lateinit var viewModel: UsersViewModel
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).getUserVM()

        observeViewModel()

        val btnSave = view.findViewById<Button>(R.id.btnSave)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        btnSave.setOnClickListener {
            val user = viewModel.userLD.value
            user?.let {
                val id = it.id
                val username = binding.txtUsername.text.toString()
                val password = binding.txtPassword.text.toString()
                val firstName = binding.txtFirstName.text.toString()
                val lastName = binding.txtLastName.text.toString()
                val photoUrl = binding.txtPhotoUrl.text.toString()

                if(password == binding.txtPasswordConfirm.text.toString()) {
                    viewModel.updateUser(id, username, password, firstName, lastName, photoUrl, {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }, {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    })
                }
                else{
                    Toast.makeText(requireContext(), "Password and the confirmation doesn't match", Toast.LENGTH_SHORT).show()
                }
            }
        }



        btnLogout.setOnClickListener {
            val action = SettingFragmentDirections.actionLogoutFragment()
            Navigation.findNavController(it).navigate(action)
            viewModel.clear()
        }
    }

    private fun observeViewModel(){
        viewModel.userLD.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                if (user.id != null) {
                    Picasso.get().load(user.profUrl).into(binding.imgProfile)
                    binding.txtUsername.setText(user.uname)
                    binding.txtFirstName.setText(user.fname)
                    binding.txtLastName.setText(user.lname)
                    binding.txtPassword.setText(user.pwd)
                    binding.txtPasswordConfirm.setText(user.pwd)
                    binding.txtPhotoUrl.setText(user.profUrl)
                } else {
                    Log.d("ReadFragment", "UserData: $user")
                }
            }
        })
        viewModel.userLD.observe(viewLifecycleOwner, Observer{
            Log.d("UpdateUser", "UserData: ${it.toString()}")
        })
    }
}