package com.htetwunsan.tmdb.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.htetwunsan.tmdb.Constant
import com.htetwunsan.tmdb.Injection
import com.htetwunsan.tmdb.databinding.ActivityUpcomingDetailBinding
import com.htetwunsan.tmdb.ui.viewmodels.UpcomingDetailViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UpcomingDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: UpcomingDetailViewModel
    private lateinit var binding : ActivityUpcomingDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpcomingDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = intent.getStringExtra("title")

        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(UpcomingDetailViewModel::class.java)

        val movieId = intent.getLongExtra("movie_id", 0)
        viewModel.movieId = movieId

        val sharedPref = getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)

        binding.favorite.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean(movieId.toString(), isChecked).apply()
        }

        binding.favorite.isChecked = sharedPref.getBoolean(movieId.toString(), false)

        initData()
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {

        lifecycleScope.launch {
            try {
                val movie = viewModel.fetchMovie()
                binding.movieId.text = "Movie Id: " + movie.id.toString()
                binding.backdropPath.text = "Backdrop Path: " + movie.backdropPath
                binding.budget.text = "Budget: " + movie.budget.toString()
                binding.homePage.text = "Home Page: " + movie.homePage
                binding.imdbId.text = "Imdb Id: " + movie.imdbId
                binding.originalLanguage.text = "Original Language: " + movie.originalLanguage
                binding.originalTitle.text = "Original title: " + movie.originalTitle
                binding.overview.text = "Overview: " + movie.overview
                binding.popularity.text = "Popularity: " + movie.popularity.toString()
                binding.posterPath.text = "Poster Path: " + movie.posterPath
                binding.releaseDate.text = "Release Date: " + movie.releaseDate
                binding.revenue.text = "Revenue: " + movie.revenue.toString()
                binding.runtime.text = "Runtime: " + movie.runtime.toString()
                binding.status.text = "Status: " + movie.status
                binding.tagLine.text = "Tag Line: " + movie.tagLine
                binding.title.text = "Title: " + movie.title
                binding.video.text = "Video: " + movie.video.toString()
                binding.voteAverage.text = "Vote Average: " + movie.voteAverage.toString()
                binding.voteCount.text = "Vote Count: " + movie.voteCount.toString()
            } catch (exception: IOException) {
                Toast.makeText(
                    applicationContext,
                    "\uD83D\uDE28 Whoops ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            } catch (exception: HttpException) {
                Toast.makeText(
                    applicationContext,
                    "\uD83D\uDE28 Whoops ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}