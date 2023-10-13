package com.example.tmbdmovieapp.model

import com.example.tmbdmovieapp.SearchResult.SearchResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmbdmovieapp.BuildConfig
import com.example.tmbdmovieapp.`interface`.MovieDbApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MainViewModel: ViewModel() {


    private val api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieDbApi::class.java)

    private val _results = MutableLiveData<List<SearchResult>>()
    val results: LiveData<List<SearchResult>> = _results

    fun search(query: String) {
        viewModelScope.launch {

            val response = api.search(BuildConfig.MOVIE_DB_API_KEY, query, 1)
            _results.value = response.results

        }
    }
}
