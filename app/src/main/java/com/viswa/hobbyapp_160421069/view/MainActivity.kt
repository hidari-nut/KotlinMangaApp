package com.viswa.hobbyapp_160421069.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.viswa.hobbyapp_160421069.R
import com.viswa.hobbyapp_160421069.viewmodel.UsersViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: UsersViewModel
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController =  (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController
        NavigationUI.setupActionBarWithNavController(this, navController)

        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    fun getUserVM(): UsersViewModel{
        return viewModel
    }

}