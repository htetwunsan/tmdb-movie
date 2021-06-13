package com.htetwunsan.tmdb.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.htetwunsan.tmdb.ui.viewholders.UpcomingLoadStateViewHolder

class UpcomingLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<UpcomingLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: UpcomingLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): UpcomingLoadStateViewHolder {
        return UpcomingLoadStateViewHolder.create(parent, retry)
    }
}