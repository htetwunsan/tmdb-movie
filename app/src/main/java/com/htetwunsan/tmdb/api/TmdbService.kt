package com.htetwunsan.tmdb.api

import com.htetwunsan.tmdb.Constant
import com.htetwunsan.tmdb.model.DetailMovie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * TMDB API communication setup via Retrofit.
 */
interface TmdbService {

    @GET("movie/upcoming?api_key=${Constant.API_KEY}")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): UpcomingResponse

    @GET("movie/{movie_id}?api_key=${Constant.API_KEY}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Long
    ): DetailMovie

    companion object {
        private const val BASE_URL = Constant.TMDB_URL + Constant.API_VERSION + '/'
        fun create(): TmdbService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TmdbService::class.java)
        }
    }
}