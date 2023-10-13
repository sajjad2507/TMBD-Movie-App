package com.example.tmbdmovieapp.`interface`

import com.example.tmbdmovieapp.SearchResult.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbApi {
    @GET("search/multi")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchResponse
}

data class SearchResponse(
    val results: List<SearchResult>
)
