package com.htetwunsan.tmdb.ui.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.htetwunsan.tmdb.data.Repository
import com.htetwunsan.tmdb.model.UpcomingMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UpcomingListViewModel(private val repository: Repository) : ViewModel() {

    private var currentResult: Flow<PagingData<UiModel.MovieItem>>? = null

    fun fetchUpcomingMovie(): Flow<PagingData<UiModel.MovieItem>> {
        val newResult: Flow<PagingData<UiModel.MovieItem>> = repository.getUpcomingMovieStream()
            .map { pagingData -> pagingData.map { UiModel.MovieItem(it) } }
            .cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }
}

sealed class UiModel {
    data class MovieItem(val movie: UpcomingMovie) : UiModel()
}