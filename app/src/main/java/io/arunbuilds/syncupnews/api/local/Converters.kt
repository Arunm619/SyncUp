package io.arunbuilds.syncupnews.api.local

import androidx.room.TypeConverter
import io.arunbuilds.syncupnews.api.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(sourceName: String): Source {
        return Source("", sourceName)
    }
}