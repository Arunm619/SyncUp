package io.arunbuilds.syncupnews.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import io.arunbuilds.syncupnews.R
import io.arunbuilds.syncupnews.ui.HomeActivity
import io.arunbuilds.syncupnews.ui.HomeViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private lateinit var viewModel: HomeViewModel
    private val args: ArticleFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).homeViewModel
        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        viewModel.errorsLiveData.observe(viewLifecycleOwner, Observer {
            Snackbar.make(view, "Error Occured :  $it", Snackbar.LENGTH_SHORT).show()
        })

        fab.setOnClickListener {
            viewModel.saveNews(article)
            Snackbar.make(view, "Saved Successfully!", Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ArticleFragment()
    }
}