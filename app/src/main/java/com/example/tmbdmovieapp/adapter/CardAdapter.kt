package com.example.tmbdmovieapp.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmbdmovieapp.MovieDetail
import com.example.tmbdmovieapp.R
import com.example.tmbdmovieapp.SearchResult.SearchResult
import com.example.tmbdmovieapp.fragments.DetailsScreen

class CardAdapter(val requireContext: Context, private val items: List<SearchResult>) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val nameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        val genre: TextView = itemView.findViewById(R.id.genre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        if (item.media_type == "movie") {
            holder.genre.text = "movie"
        } else {
            holder.genre.text = "TV Show"
        }
        holder.nameTextView.text = item.title
        Glide
            .with(requireContext)
            .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
            .centerCrop()
            .into(holder.imageView)

        val animation = AnimationUtils.loadAnimation(requireContext, R.anim.slide_out_top)
        holder.itemView.startAnimation(animation)

        holder.itemView.setOnClickListener {

            var mGenre = "General"

            if (item.genre_ids[0].toString() != null) {

                if (mGenre == "12") {
                    mGenre = "Adventure"
                } else if(mGenre == "28") {
                    mGenre = "Action"
                } else if(mGenre == "53") {
                    mGenre = "Thriller"
                } else if(mGenre == "878") {
                    mGenre = "Science Fiction"
                } else if(mGenre == "10765") {
                    mGenre = "SciFi / Fantasy"
                } else {
                    mGenre = "General"
                }
            } else {
                mGenre = "General"
            }

            val mTitle = item.title
            val mOverview = item.overview
            val mBackdrop = item.backdrop_path
            val mPoster = item.poster_path
            val mMediaType = item.media_type
            val mReleaseDate = item.release_date
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
//        return items.size
        return 20
    }
    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.itemView.clearAnimation()
    }
}
