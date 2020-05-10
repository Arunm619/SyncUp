package io.arunbuilds.syncupnews.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.arunbuilds.syncupnews.api.model.Article
import io.arunbuilds.syncupnews.api.model.NewsResponse
import io.arunbuilds.syncupnews.repository.NewsRepository
import io.arunbuilds.syncupnews.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber

class HomeViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPageNumber = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPageNumber = 1

    val errorsLiveData: MutableLiveData<String> = MutableLiveData()

    fun getBreakingNews(countryCode: String) {
        viewModelScope.launch {
            breakingNews.postValue(Resource.Loading())
            try {
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPageNumber)
                breakingNews.postValue(handleResponse(response))
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown Exception Occured"
                breakingNews.postValue(Resource.Error(errorMessage))
            }
        }
    }

    fun searchNews(query: String) {
        viewModelScope.launch {
            searchNews.postValue(Resource.Loading())
            try {
                val response = newsRepository.searchNews(query, searchNewsPageNumber)
                searchNews.postValue(handleResponse(response))
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown Exception Occured"
                searchNews.postValue(Resource.Error(errorMessage))
            }
        }
    }

    fun saveNews(article: Article) = viewModelScope.launch {
        try {
            newsRepository.upsert(article)
        } catch (exception: Exception) {
            Timber.e(exception)
            errorsLiveData.postValue(exception.message)
        }
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteNews(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    private fun handleResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}