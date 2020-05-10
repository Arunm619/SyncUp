package io.arunbuilds.syncupnews.api.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.arunbuilds.syncupnews.api.model.Article

@Database(
    entities = [Article::class],
    version = 3
)

@TypeConverters(
    Converters::class
)
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDAO

    companion object {
        @Volatile
        private var INSTANCE: ArticleDataBase? = null

        private val LOCK = Any()
        operator fun invoke(context: Context) = INSTANCE ?: synchronized(lock = LOCK) {
            INSTANCE ?: createDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ArticleDataBase::class.java,
            "article_db.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}