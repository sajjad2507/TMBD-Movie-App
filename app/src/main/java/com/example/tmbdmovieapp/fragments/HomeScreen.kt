package com.example.tmbdmovieapp.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import com.example.tmbdmovieapp.R
import com.example.tmbdmovieapp.adapter.CardAdapter
import com.example.tmbdmovieapp.databinding.FragmentHomeScreenBinding
import com.example.tmbdmovieapp.model.MainViewModel
import com.example.tmbdmovieapp.room.AppDatabase
import com.example.tmbdmovieapp.room.Movies
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class HomeScreen : Fragment() {

    lateinit var binding: FragmentHomeScreenBinding
    private lateinit var viewModel: MainViewModel
    private var backPressedTime: Long = 0
    private val doubleBackToExitPressedMessage = "Press back again to exit"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                
                if (isNetworkAvailable(requireContext())) {
                    if (query != null) {
                        viewModel.search(query)
                        hideKeyboard()
                        binding.searchView.clearFocus()
                        return true
                    }
                } else {

                    val dialogBuilder = AlertDialog.Builder(requireActivity())

                    dialogBuilder.setTitle("Network Issue!")
                    dialogBuilder.setMessage("Network problem, would you like to see MyWatchList")

                    dialogBuilder.setPositiveButton("OK") { dialog, which ->

                        NavHostFragment.findNavController(requireParentFragment())
                            .navigate(R.id.action_homeScreen_to_myWatchList2)
                        dialog.dismiss()

                        return@setPositiveButton

                    }

                    dialogBuilder.setNegativeButton("Cancel") { dialog, which ->

                        dialog.dismiss()

                    }

                    val dialog = dialogBuilder.create()
                    dialog.show()

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel.results.observe(requireActivity()) { results ->

            val cardAdapter = CardAdapter(requireContext(), results)
            binding.recyclerView.apply {
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                setHasFixedSize(true)
                adapter = cardAdapter

            }
        }

        binding.savedMovies.setOnClickListener {

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_homeScreen_to_myWatchList2)

        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    requireActivity().finish()
                    return
                } else {
                    Toast.makeText(requireContext(), doubleBackToExitPressedMessage, Toast.LENGTH_SHORT).show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        })

        return binding.root
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}