package com.htetwunsan.tmdb.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.htetwunsan.tmdb.model.UpcomingMovie
import com.htetwunsan.tmdb.ui.viewholders.UpcomingViewHolder
import com.htetwunsan.tmdb.ui.viewmodels.UiModel

/**
 * Adapter for the list of upcoming movies.
 */
class UpcomingAdapter : PagingDataAdapter<UiModel.MovieItem, UpcomingViewHolder>(COMPARATOR){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        return UpcomingViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val uiModel = getItem(position)
        holder.bind(uiModel?.movie, position)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<UiModel.MovieItem>() {
            override fun areItemsTheSame(oldItem: UiModel.MovieItem, newItem: UiModel.MovieItem): Boolean =
                oldItem.movie.id == newItem.movie.id

            override fun areContentsTheSame(oldItem: UiModel.MovieItem, newItem: UiModel.MovieItem): Boolean =
                oldItem == newItem
        }
    }
}