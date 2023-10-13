package com.example.tmbdmovieapp.room

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.tmbdmovieapp.MovieDetail
import com.example.tmbdmovieapp.R
import kotlinx.coroutines.*
import java.util.ArrayList

class MovieAdapter(
    val requireContext: Context,
    var movieList: ArrayList<Movies>,
    val requireParentFragment: Fragment,
) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    var movie: List<Movies>? = null

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePoster = itemView.findViewById<ImageView>(R.id.movie_poster)
        val releaseDate = itemView.findViewById<TextView>(R.id.movie_release_date)
        val movieTitle = itemView.findViewById<TextView>(R.id.movie_title)
        val movieOverview = itemView.findViewById<TextView>(R.id.movie_overview)

    }

    fun setFilteredList(model: ArrayList<Movies>) {
        this.movieList = model
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.movie_item1, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        val item = movieList[position]

        holder.movieTitle.text = item.title
        holder.movieOverview.text = item.overview
        holder.releaseDate.text = item.release_date
        Glide
            .with(requireContext)
            .load("https://image.tmdb.org/t/p/w500${movieList[position].poster_path}")
            .centerCrop()
            .into(holder.moviePoster)

        val animation = AnimationUtils.loadAnimation(requireContext, R.anim.slide_out_top)
        holder.itemView.startAnimation(animation)

        holder.itemView.setOnLongClickListener {

            val dialogBuilder = AlertDialog.Builder(requireContext)
            val inflater = LayoutInflater.from(requireContext)

            val db = Room.databaseBuilder(
                requireContext,
                AppDatabase::class.java, "movieDatabase"
            ).build()


            dialogBuilder.setTitle("Delete File")
            dialogBuilder.setMessage("Are you sure yor want to delete this movie?")

            dialogBuilder.setPositiveButton("OK") { dialog, which ->
                val userDao = db.userDao()

                CoroutineScope(Dispatchers.IO).launch {
                    userDao.delete(item)

                    withContext(Dispatchers.Main) {
                        movieList.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
            }

            dialogBuilder.setNegativeButton("Cancel") { dialog, which ->

                Toast.makeText(requireContext, "Request Cancelled", Toast.LENGTH_SHORT).show()
                dialog.dismiss()

            }

            val dialog = dialogBuilder.create()
            dialog.show()

            true
        }

        holder.itemView.setOnClickListener {

            val mTitle = item.title
            val mOverview = item.overview
            val mBackdrop = item.backdrop_path
            val mPoster = item.poster_path
            val mMediaType = item.media_type
            val mReleaseDate = item.release_date
            val mGenre = item.genre_ids
            val mVote = item.vote_average

            val sharedPreference: SharedPreferences? =
                requireContext?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            var editor = sharedPreference?.edit()

            editor?.putString("title", mTitle)
            editor?.putString("overview", mOverview)
            editor?.putString("backdrop", mBackdrop)
            editor?.putString("poster", mPoster)
            editor?.putString("mediatype", mMediaType)
            editor?.putString("releasetype", mReleaseDate)
            editor?.putString("genre", mGenre.toString())
            editor?.putString("vote", mVote)
            editor?.commit()

            val intent = Intent(requireContext, MovieDetail::class.java)
            requireContext.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}