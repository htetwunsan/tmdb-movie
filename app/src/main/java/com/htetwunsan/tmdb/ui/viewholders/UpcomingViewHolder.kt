package com.htetwunsan.tmdb.ui.viewholders

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.htetwunsan.tmdb.Constant
import com.htetwunsan.tmdb.R
import com.htetwunsan.tmdb.model.UpcomingMovie
import com.htetwunsan.tmdb.ui.activities.UpcomingDetailActivity

class UpcomingViewHolder(view: View) :  RecyclerView.ViewHolder(view) {

    private val adult = view.findViewById<TextView>(R.id.adult)
    private val backdropPath = view.findViewById<TextView>(R.id.backdrop_path)
    private val genreIds = view.findViewById<TextView>(R.id.genre_ids)
    private val itemId = view.findViewById<TextView>(R.id.item_id)
    private val originalLanguage = view.findViewById<TextView>(R.id.original_language)
    private val posterPath = view.findViewById<TextView>(R.id.poster_path)
    private val releaseDate = view.findViewById<TextView>(R.id.release_date)
    private val title = view.findViewById<TextView>(R.id.title)
    private val video = view.findViewById<TextView>(R.id.video)
    private val originalTitle = view.findViewById<TextView>(R.id.original_title)
    private val overview = view.findViewById<TextView>(R.id.overview)
    private val popularity = view.findViewById<TextView>(R.id.popularity)
    private val voteAverage = view.findViewById<TextView>(R.id.vote_average)
    private val voteCount = view.findViewById<TextView>(R.id.vote_count)
    private val favorite = view.findViewById<SwitchMaterial>(R.id.favorite)

    private var movie: UpcomingMovie? = null

    private var index: Int = 0

    private var sharedPref: SharedPreferences

    init {
        sharedPref = view.context.getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)

        view.setOnClickListener {
            val intent = Intent(it.context, UpcomingDetailActivity::class.java)
            intent.putExtra("movie_id", movie?.id)
            intent.putExtra("title", movie?.originalTitle)
            sharedPref.edit().putInt(Constant.LAST_INDEX_TO_UPDATE, index).apply()
            it.context.startActivity(intent)
//            val action = UpcomingListFragmentDirections.mainToDetail()
//            view.findNavController().navigate(action)
        }

        favorite.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean(movie?.id.toString(), isChecked).apply()
        }
    }

    fun bind(movie: UpcomingMovie?, position: Int) {
        this.index = position
        if (movie == null) {
            val resources = itemView.resources
            adult.visibility = View.GONE
            backdropPath.visibility = View.GONE
            genreIds.visibility = View.GONE
            itemId.visibility = View.GONE
            originalLanguage.visibility = View.GONE
            posterPath.visibility = View.GONE
            releaseDate.visibility = View.GONE
            title.visibility = View.GONE
            video.visibility = View.GONE
            originalTitle.text = resources.getString(R.string.loading)
            overview.visibility = View.GONE
            popularity.text = resources.getString(R.string.unknown)
            voteAverage.text = resources.getString(R.string.unknown)
            voteCount.text = resources.getString(R.string.unknown)
            favorite.visibility = View.GONE
        } else {
            showMovieData(movie)
        }
    }

    private fun showMovieData(movie: UpcomingMovie) {
        this.movie = movie
        favorite.isChecked = sharedPref.getBoolean(movie.id.toString(), false)
        adult.text = "Adult:" + movie.adult.toString()
        if (movie.backdropPath != null) {
            backdropPath.text = "Backdrop Path:" +movie.backdropPath
        } else {
            backdropPath.visibility = View.GONE
        }
        genreIds.text = "Genre Ids: " + movie.genreIds.toString()
        itemId.text = "Item Id: " + movie.id.toString()
        originalLanguage.text = "Original Language: " + movie.originalLanguage.toString()
        if (movie.posterPath != null) {
            posterPath.text = "Poster Path: " + movie.posterPath
        } else {
            posterPath.visibility = View.GONE
        }
        releaseDate.text = "Release Date: " + movie.releaseDate
        title.text = "Title: " + movie.title
        video.text = "Video: " + movie.video.toString()
        originalTitle.text = "Original Title: " + movie.originalTitle
        overview.text = "Overview: " + movie.overview
        popularity.text = "Popularity: " + movie.popularity.toString()
        voteAverage.text = "Vote Average: " + movie.voteAverage.toString()
        voteCount.text = "Vote Count: " + movie.voteCount.toString()
    }

    companion object {
        fun create(parent: ViewGroup): UpcomingViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.upcoming_view_item, parent, false)
            return UpcomingViewHolder(view)
        }
    }
}