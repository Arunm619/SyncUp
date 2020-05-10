package io.arunbuilds.syncupnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.arunbuilds.syncupnews.R
import io.arunbuilds.syncupnews.adapters.NewsAdapter
import io.arunbuilds.syncupnews.ui.HomeActivity
import io.arunbuilds.syncupnews.ui.HomeViewModel
import io.arunbuilds.syncupnews.util.Constants
import kotlinx.android.synthetic.main.fragment_saved_news.*


class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    private lateinit var viewModel: HomeViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).homeViewModel
        setupRecyclerView()
        newsAdapter.setOnclickListener {
            val bundle = Bundle().apply {
                putSerializable(Constants.KEY_ARTICLE, it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {
            newsAdapter.submitList(it)
        })


    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.getArticleAt(position)
                viewModel.deleteNews(article)
                Snackbar.make(view!!, "Successfully Delete article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveNews(article)
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvSavedNews)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SavedNewsFragment()
    }
}