package com.example.tmbdmovieapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movies(

    @PrimaryKey val title: String,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "release_date") val release_date: String?,
    @ColumnInfo(name = "backdrop_path") val backdrop_path: String?,
    @ColumnInfo(name = "media_type") val media_type: String?,
    @ColumnInfo(name = "genre_ids") val genre_ids: String?,
    @ColumnInfo(name = "vote_average") val vote_average: String?,
    @ColumnInfo(name = "poster_path") val poster_path: String?

)