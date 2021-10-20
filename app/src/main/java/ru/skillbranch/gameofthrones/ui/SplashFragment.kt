package ru.skillbranch.gameofthrones.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import ru.skillbranch.gameofthrones.R


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val container = view.findViewById<ImageView>(R.id.splash_image)
        container.setOnClickListener {
            findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToHouseFragment()
            )
        }
    }
}