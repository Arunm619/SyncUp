package io.arunbuilds.syncupnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.arunbuilds.syncupnews.repository.NewsRepository

class NewsViewModelFactory(
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(newsRepository = newsRepository) as T
        }
        throw RuntimeException("No View Model Found")
    }
}