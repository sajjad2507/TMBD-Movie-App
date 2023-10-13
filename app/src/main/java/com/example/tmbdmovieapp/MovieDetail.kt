package com.example.tmbdmovieapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.tmbdmovieapp.databinding.ActivityMovieDetailBinding
import com.example.tmbdmovieapp.room.AppDatabase
import com.example.tmbdmovieapp.room.MovieAdapter
import com.example.tmbdmovieapp.room.MovieDao
import com.example.tmbdmovieapp.room.Movies
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetail : AppCompatActivity() {
//class MovieDetail : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

//    private val DIALOG_REQUEST_CODE = 1
//    val API_KEY = "AIzaSyAelvu9vbehRef_9gqMKewDcvgtvvS2pzU"
//    val YOUTUBE_VIDEO_ID = "HzeK7g8cD0Y"

    @Inject
    lateinit var db: AppDatabase

    val playerView by lazy { YouTubePlayerView(this) }
    lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        supportActionBar?.hide()

//        val layout = layoutInflater.inflate(R.layout.activity_movie_detail, null) as LinearLayout
//
//        playerView.layoutParams = LinearLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
//        )
////        layout.addView(playerView)
//        val childLayout = binding.mainLayout as LinearLayout
//        childLayout.addView(playerView)

        val sharedPreference: SharedPreferences? =
            this?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        val mTitle = sharedPreference?.getString("title", "Not Available")
        val mOverview = sharedPreference?.getString("overview", "Not Available")
        val mBackdrop = sharedPreference?.getString("backdrop", "Not Available")
        val mPoster = sharedPreference?.getString("poster", "Not Available")
        val mMediaType = sharedPreference?.getString("mediatype", "Not Available")
        val mReleaseDate = sharedPreference?.getString("releasetype", "Not Available")
        var mGenre = sharedPreference?.getString("genre", "Not Available")
        val mVote = sharedPreference?.getString("vote", "Not Available")


        binding.movieTtile.text = mTitle
        binding.title.text = mTitle
        binding.overview.text = mOverview
        Glide
            .with(this)
            .load("https://image.tmdb.org/t/p/w500${mBackdrop}")
            .centerCrop()
            .into(binding.banner)
        Glide
            .with(this)
            .load("https://image.tmdb.org/t/p/w500${mPoster}")
            .centerCrop()
            .into(binding.poster)
        binding.release.text = mReleaseDate

        binding.adventure.text = mGenre
        binding.genre.text = mGenre
        binding.vote.text = mVote
        binding.releaseDate.text = mReleaseDate

        val userDao = db.userDao()

        binding.addToList.setOnClickListener {

            lifecycleScope.launch {

                if (mTitle != "Not Available") {

                    val obj = Movies(
                        mTitle.toString(),
                        mOverview,
                        mReleaseDate,
                        mBackdrop,
                        mMediaType,
                        mGenre,
                        mVote,
                        mPoster
                    )

                    userDao.insertAll(obj)

                    Toast.makeText(this@MovieDetail, "Check the Watch List", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(this@MovieDetail, "It must have Title", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

//        playerView.initialize(API_KEY, this)

        setContentView(binding.root)
    }
//
//    override fun onInitializationSuccess(
//        provider: YouTubePlayer.Provider?, youtubePlayer: YouTubePlayer?,
//        wasRestored: Boolean
//    ) {
//
//        if(!wasRestored){
//            youtubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
//            youtubePlayer?.pause()
//        }else{
//            youtubePlayer?.pause()
//        }
//    }
//
//    override fun onInitializationFailure(
//        provider: YouTubePlayer.Provider?,
//        youtubeInitializationResult: YouTubeInitializationResult?
//    ) {
//        if (youtubeInitializationResult?.isUserRecoverableError == true) {
//            youtubeInitializationResult.getErrorDialog(this, DIALOG_REQUEST_CODE).show()
//        } else {
//            val errorMessage =
//                "There was an error initializing the YoutubePlayer ($youtubeInitializationResult)"
//            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(requestCode == DIALOG_REQUEST_CODE){
//            playerView.initialize(API_KEY, this)
//        }
//    }
}