package com.htetwunsan.tmdb

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.htetwunsan.tmdb.api.TmdbService
import com.htetwunsan.tmdb.data.Repository
import com.htetwunsan.tmdb.db.UpcomingDatabase
import com.htetwunsan.tmdb.ui.viewmodels.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    private fun provideRepository(context: Context): Repository {
        return Repository(TmdbService.create(), UpcomingDatabase.getInstance(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideRepository(context))
    }
}