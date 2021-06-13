package com.htetwunsan.tmdb.ui.activities

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.htetwunsan.tmdb.Constant
import com.htetwunsan.tmdb.Injection
import com.htetwunsan.tmdb.databinding.ActivityUpcomingListBinding
import com.htetwunsan.tmdb.ui.adapters.UpcomingAdapter
import com.htetwunsan.tmdb.ui.adapters.UpcomingLoadStateAdapter
import com.htetwunsan.tmdb.ui.viewmodels.UpcomingListViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class UpcomingListActivity : AppCompatActivity() {

    private lateinit var viewModel: UpcomingListViewModel
    private lateinit  var binding: ActivityUpcomingListBinding
    private val adapter = UpcomingAdapter()

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpcomingListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = "Home"

        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(UpcomingListViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        initAdapter()
        fetch()
        binding.retryButton.setOnClickListener { adapter.retry() }
    }

    override fun onResume() {
        super.onResume()
        val position = getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
            .getInt(Constant.LAST_INDEX_TO_UPDATE, 0)
        adapter.notifyItemChanged(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("UpcomingListActivity", "Destroyed")
    }


    private fun fetch() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.fetchUpcomingMovie().collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.list.scrollToPosition(0) }
        }
    }

    private fun initAdapter() {
        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = UpcomingLoadStateAdapter { adapter.retry() },
            footer = UpcomingLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            // Only show the list if refresh succeeds.
            binding.list.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Whoops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
        }
    }
}