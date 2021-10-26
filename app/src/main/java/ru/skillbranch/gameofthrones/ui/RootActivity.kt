package ru.skillbranch.gameofthrones.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import ru.skillbranch.gameofthrones.R

class RootActivity : AppCompatActivity() {

    private lateinit var viewModel: RootViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        viewModel = ViewModelProvider(this).get(RootViewModel::class.java)
        viewModel.isDataSynchronized.observe(this) {
            findNavController(this, R.id.nav_host_fragment).navigate(
                SplashFragmentDirections.actionSplashFragmentToHousesFragment()
            )
        }
        viewModel.synchronizeData()
    }
}