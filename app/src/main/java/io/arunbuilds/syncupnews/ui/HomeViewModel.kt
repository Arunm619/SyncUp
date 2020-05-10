package io.arunbuilds.syncupnews.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.arunbuilds.syncupnews.api.model.NewsResponse
import io.arunbuilds.syncupnews.repository.NewsRepository
import io.arunbuilds.syncupnews.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    init {
        getBreakingNews("IN")
    }


    fun getBreakingNews(countryCode: String) {
        viewModelScope.launch {
            breakingNews.postValue(Resource.Loading())
            try {
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handleResponse(response))
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown Exception Occured"
                breakingNews.postValue(Resource.Error(errorMessage))
            }
        }
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