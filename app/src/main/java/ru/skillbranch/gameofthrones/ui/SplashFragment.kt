package ru.skillbranch.gameofthrones.ui

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.skillbranch.gameofthrones.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val va = ValueAnimator.ofFloat(0.5f, 0f).apply {
            interpolator = CycleInterpolator(2f )
            duration = 4000
            repeatCount = Animation.INFINITE
        }
        va.addUpdateListener { binding.splashRedOverlay.alpha = it.animatedValue as Float }
        va.start()

        binding.splashRedOverlay.setOnClickListener {
            va.removeAllUpdateListeners()
            va.cancel()
            findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToHouseFragment()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}