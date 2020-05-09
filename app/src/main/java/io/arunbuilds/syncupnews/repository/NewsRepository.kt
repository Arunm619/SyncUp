package io.arunbuilds.syncupnews.repository

import android.content.Context
import io.arunbuilds.syncupnews.api.local.ArticleDataBase
import io.arunbuilds.syncupnews.api.remote.NewsApi
import io.arunbuilds.syncupnews.api.remote.RetrofitInstance

class NewsRepository(
    private val context: Context,
    val db: ArticleDataBase = ArticleDataBase(context),
    val api: NewsApi = RetrofitInstance.api
)