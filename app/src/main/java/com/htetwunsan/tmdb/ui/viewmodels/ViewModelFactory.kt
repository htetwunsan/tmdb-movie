package com.htetwunsan.tmdb.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.htetwunsan.tmdb.Injection
import com.htetwunsan.tmdb.data.Repository

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpcomingListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpcomingListViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(UpcomingDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpcomingDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
