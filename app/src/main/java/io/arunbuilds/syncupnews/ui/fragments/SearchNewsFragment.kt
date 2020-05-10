package io.arunbuilds.syncupnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.arunbuilds.syncupnews.R
import io.arunbuilds.syncupnews.adapters.NewsAdapter
import io.arunbuilds.syncupnews.api.model.NewsResponse
import io.arunbuilds.syncupnews.ui.HomeActivity
import io.arunbuilds.syncupnews.ui.HomeViewModel
import io.arunbuilds.syncupnews.util.Constants.SEARCH_NEWS_TIME_DELAY
import io.arunbuilds.syncupnews.util.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber


class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    lateinit var viewModel: HomeViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).homeViewModel
        setupRecyclerView()

        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchNews(it.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {
            handleState(it)
        })
    }

    private fun handleState(newsResponse: Resource<NewsResponse>) {
        when (newsResponse) {
            is Resource.Success -> {
                hideProgressBar()
                newsResponse.data?.let {
                    newsAdapter.submitList(it.articles)
                }
            }

            is Resource.Error -> {
                hideProgressBar()
                Timber.e(newsResponse.message)
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "Error Occured : ${newsResponse.message}",
                    Snackbar.LENGTH_SHORT
                ).show()

            }

            is Resource.Loading -> {
                showProgressBar()
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchNewsFragment()
    }
}