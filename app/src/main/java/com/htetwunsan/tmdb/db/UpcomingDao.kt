package com.htetwunsan.tmdb.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.htetwunsan.tmdb.model.UpcomingMovie

@Dao
interface UpcomingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<UpcomingMovie>)

    @Query("SELECT * FROM upcoming_movies ORDER BY popularity DESC")
    fun retrieve(): PagingSource<Int, UpcomingMovie>

    @Query("DELETE FROM upcoming_movies")
    suspend fun deleteAll()
}