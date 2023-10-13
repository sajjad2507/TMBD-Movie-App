package com.example.tmbdmovieapp.room

import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<Movies>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: Movies)

    @Delete
    suspend fun delete(user: Movies)
}