package io.arunbuilds.syncupnews.ui.fragments

import androidx.fragment.app.Fragment
import io.arunbuilds.syncupnews.R

class ArticleFragment : Fragment(R.layout.fragment_article) {
    companion object {
        @JvmStatic
        fun newInstance() =
            ArticleFragment()
    }
}