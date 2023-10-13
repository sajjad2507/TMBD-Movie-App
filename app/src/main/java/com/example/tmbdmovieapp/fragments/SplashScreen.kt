package com.example.tmbdmovieapp.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.navigation.fragment.NavHostFragment
import com.example.tmbdmovieapp.R

class SplashScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        val imgView = view.findViewById<ImageView>(R.id.imgView)

        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_top_splash)
        imgView?.startAnimation(animation)

        Handler(Looper.getMainLooper()).postDelayed({

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_splashScreen_to_homeScreen)

    }, 3000)

        return view
    }
}