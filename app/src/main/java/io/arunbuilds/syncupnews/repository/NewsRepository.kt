package io.arunbuilds.syncupnews.repository

import android.content.Context
import io.arunbuilds.syncupnews.api.local.ArticleDataBase
import io.arunbuilds.syncupnews.api.remote.NewsApi
import io.arunbuilds.syncupnews.api.remote.RetrofitInstance

class NewsRepository(
    private val context: Context,
    private val newsDb: ArticleDataBase = ArticleDataBase(context),
    private val newsApi: NewsApi = RetrofitInstance.api
) {
    suspend fun getBreakingNews(countryCode: String, page: Int) =
        newsApi.getBreakingNews(countryCode, page)

}