package ru.skillbranch.gameofthrones.ui

import android.os.Bundle
import android.widget.Toast
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
        observeSynchronization()
        if (savedInstanceState == null) {
            viewModel.synchronizeData()
        }
    }

    fun observeSynchronization() {
        viewModel.isDataSynchronized.observe(this) {
            when (it?.getContentIfNotHandled()) {
                RootViewModel.SynchronizationResult.FINISHED -> {
                    findNavController(this, R.id.nav_host_fragment).navigate(
                        SplashFragmentDirections.actionSplashFragmentToHousesFragment()
                    )
                }
                RootViewModel.SynchronizationResult.NO_CONNECTION -> {
                    Toast.makeText(
                        applicationContext,
                        resources.getString(R.string.no_connection_toast),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}