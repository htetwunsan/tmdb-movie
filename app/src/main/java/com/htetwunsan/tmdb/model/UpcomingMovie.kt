package com.htetwunsan.tmdb.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


/**
 * Objects of this type are received from the TMDB API, therefore all the fields are annotated
 * with the serialized name.
 * This class also defines the Room repos table, where the repo [id] is the primary key.
 */

@Entity(tableName = "upcoming_movies")
@TypeConverters(IntListConverter::class)
data class UpcomingMovie(
    @PrimaryKey @field:SerializedName("id") val id: Long,
    @field:SerializedName("adult") val adult: Boolean,
    @field:SerializedName("genre_ids") val genreIds: List<Int>,
    @field:SerializedName("backdrop_path") val backdropPath: String?,
    @field:SerializedName("original_language") val originalLanguage: String,
    @field:SerializedName("original_title") val originalTitle: String,
    @field:SerializedName("overview") val overview: String,
    @field:SerializedName("popularity") val popularity: Double,
    @field:SerializedName("poster_path") val posterPath: String?,
    @field:SerializedName("release_date") val releaseDate: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("video") val video: Boolean,
    @field:SerializedName("vote_average") val voteAverage: Double,
    @field:SerializedName("vote_count") val voteCount: Long
)

@ProvidedTypeConverter
class IntListConverter {
    var gson = Gson()
    @TypeConverter
    fun stringToIntList(data: String): List<Int> {
        val listType: Type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson<List<Int>>(data, listType)
    }

    @TypeConverter
    fun intListToString(ints: List<Int>): String {
        return gson.toJson(ints)
    }
}