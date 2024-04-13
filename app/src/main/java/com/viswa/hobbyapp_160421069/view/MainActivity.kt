package com.viswa.hobbyapp_160421069.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.viswa.hobbyapp_160421069.R
import com.viswa.hobbyapp_160421069.databinding.ActivityMainBinding
import com.viswa.hobbyapp_160421069.viewmodel.UsersViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: UsersViewModel
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController =  (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController

         navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d("Current Destination Label", destination.label.toString())
            if (destination.label == "fragment_home" || destination.label == "fragment_login") {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
                NavigationUI.setupActionBarWithNavController(this, navController)
            }
        }
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    fun getUserVM(): UsersViewModel{
        return viewModel
    }

}