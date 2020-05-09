package io.arunbuilds.syncupnews.api.local

import androidx.lifecycle.LiveData
import androidx.room.*
import io.arunbuilds.syncupnews.api.model.Article

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertorUpdateArticle(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}