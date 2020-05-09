package io.arunbuilds.syncupnews.ui

import androidx.lifecycle.ViewModel
import io.arunbuilds.syncupnews.repository.NewsRepository

class HomeViewModel(
    val newsRepository : NewsRepository
) : ViewModel() {
}