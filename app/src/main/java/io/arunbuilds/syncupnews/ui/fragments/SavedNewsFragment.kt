package io.arunbuilds.syncupnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.arunbuilds.syncupnews.R
import io.arunbuilds.syncupnews.ui.HomeActivity
import io.arunbuilds.syncupnews.ui.HomeViewModel


class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).homeViewModel

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SavedNewsFragment()
    }
}