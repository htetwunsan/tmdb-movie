package com.htetwunsan.tmdb.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.htetwunsan.tmdb.api.TmdbService
import com.htetwunsan.tmdb.db.UpcomingDatabase
import com.htetwunsan.tmdb.model.DetailMovie
import com.htetwunsan.tmdb.model.UpcomingMovie
import kotlinx.coroutines.flow.Flow

/**
 * Repository class that works with local and remote data sources.
 */
class Repository(
    private val service: TmdbService,
    private val database: UpcomingDatabase
    ) {

    fun getUpcomingMovieStream(): Flow<PagingData<UpcomingMovie>> {

        val pagingSourceFactory = { database.upcomingDao().retrieve() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = UpcomingRemoteMediator(
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getDetailResult(movieId: Long): DetailMovie {
        return service.getMovieDetail(movieId)
    }
}