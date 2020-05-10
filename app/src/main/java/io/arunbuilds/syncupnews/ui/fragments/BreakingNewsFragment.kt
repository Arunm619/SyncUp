package io.arunbuilds.syncupnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.arunbuilds.syncupnews.R
import io.arunbuilds.syncupnews.adapters.NewsAdapter
import io.arunbuilds.syncupnews.api.model.NewsResponse
import io.arunbuilds.syncupnews.ui.HomeActivity
import io.arunbuilds.syncupnews.ui.HomeViewModel
import io.arunbuilds.syncupnews.util.Constants.KEY_ARTICLE
import io.arunbuilds.syncupnews.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import timber.log.Timber

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private lateinit var viewModel: HomeViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).homeViewModel
        setupRecyclerView()
        newsAdapter.setOnclickListener {
            val bundle = Bundle().apply {
                putSerializable(KEY_ARTICLE, it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
        viewModel.getBreakingNews("IN")
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {
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
        rvBreakingNews.apply {
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
            BreakingNewsFragment()
    }
}