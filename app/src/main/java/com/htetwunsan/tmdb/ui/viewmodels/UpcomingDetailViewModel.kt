package com.htetwunsan.tmdb.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.htetwunsan.tmdb.data.Repository
import com.htetwunsan.tmdb.model.DetailMovie

class UpcomingDetailViewModel(private val repository: Repository) : ViewModel() {
    var movieId: Long = 0
    suspend fun fetchMovie() : DetailMovie {
        return repository.getDetailResult(movieId)
    }
}