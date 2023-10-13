package com.example.tmbdmovieapp.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.tmbdmovieapp.R
import com.example.tmbdmovieapp.databinding.FragmentDetailsScreenBinding
import com.example.tmbdmovieapp.room.AppDatabase
import com.example.tmbdmovieapp.room.MovieAdapter
import com.example.tmbdmovieapp.room.Movies
import com.google.android.youtube.player.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailsScreen : Fragment() {

    lateinit var binding: FragmentDetailsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsScreenBinding.inflate(layoutInflater, container, false)


        val sharedPreference: SharedPreferences? =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val movieTitle = sharedPreference?.getString("title", "")

        Toast.makeText(requireContext(), movieTitle.toString(), Toast.LENGTH_SHORT).show()

        binding.tv.text = movieTitle.toString()

        return binding.root
    }
}