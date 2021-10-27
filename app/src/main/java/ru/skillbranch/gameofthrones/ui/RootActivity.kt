package ru.skillbranch.gameofthrones.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.skillbranch.gameofthrones.R

class RootActivity : AppCompatActivity() {

    private lateinit var viewModel: RootViewModel
    private lateinit var containerView: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        viewModel = ViewModelProvider(this).get(RootViewModel::class.java)
        observeSynchronization()
        if (savedInstanceState == null) {
            viewModel.synchronizeData()
        }
        containerView = findViewById<FragmentContainerView>(R.id.nav_host_fragment)
    }

    private fun observeSynchronization() {
        viewModel.isDataSynchronized.observe(this) {
            when (it?.getContentIfNotHandled()) {
                RootViewModel.SynchronizationResult.FINISHED -> {
                    findNavController(this, R.id.nav_host_fragment).navigate(
                        SplashFragmentDirections.actionSplashFragmentToHousesFragment()
                    )
                }
                RootViewModel.SynchronizationResult.NO_CONNECTION -> {
                    Snackbar.make(
                        containerView,
                        resources.getString(R.string.no_connection_msg),
                        Snackbar.LENGTH_INDEFINITE
                    ).show()
                }
            }
        }
    }
}