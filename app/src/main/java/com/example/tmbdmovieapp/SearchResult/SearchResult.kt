package com.example.tmbdmovieapp.SearchResult

data class SearchResult(
    val title: String,
    val overview: String?,
    val backdrop_path: String?,
    val poster_path: String?,
    val media_type: String?,
    val release_date: String?,
    val genre_ids: List<Int>,
    val vote_average: String?
)