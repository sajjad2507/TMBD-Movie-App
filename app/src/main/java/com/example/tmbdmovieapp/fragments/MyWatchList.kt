package com.example.tmbdmovieapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.tmbdmovieapp.R
import com.example.tmbdmovieapp.databinding.FragmentMyWatchListBinding
import com.example.tmbdmovieapp.room.AppDatabase
import com.example.tmbdmovieapp.room.MovieAdapter
import com.example.tmbdmovieapp.room.Movies
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MyWatchList : Fragment() {

    lateinit var binding: FragmentMyWatchListBinding
    var movie: List<Movies>? = null
    private lateinit var listOfMovie: ArrayList<Movies>
    lateinit var madapter: MovieAdapter
    @Inject
    lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyWatchListBinding.inflate(layoutInflater, container, false)

        val userDao = db.userDao()

        lifecycleScope.launch {

            movie = userDao.getAll()

            listOfMovie = movie as ArrayList<Movies>

            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            madapter = MovieAdapter(requireContext(), listOfMovie, requireParentFragment())
            binding.recyclerView.adapter = madapter

        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                filterList(newText)
                return false
            }

        })


        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val fragment = HomeScreen()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, fragment)
                transaction.addToBackStack(null)
                transaction.commit()


                    }
        })

        binding.backBtn.setOnClickListener {

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_myWatchList2_to_homeScreen)

        }

        return binding.root

    }

    private fun filterList(newText: String?) {
        if (newText != null) {
            val filterList = ArrayList<Movies>()
            for (i in listOfMovie) {
                if (i.title.lowercase(Locale.ROOT)
                        .contains(newText) || i.overview?.lowercase(Locale.ROOT)!!
                        .contains(newText)
                ) {
                    filterList.add(i)
                }
            }
            if (filterList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
            } else {
                madapter.setFilteredList(filterList)
            }
        }
    }

}