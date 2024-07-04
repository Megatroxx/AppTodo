package com.example.apptodo.data.entity

import androidx.room.TypeConverter

class RelevanceConverter {
    @TypeConverter
    fun fromRelevance(value: Relevance): String {
        return value.name
    }

    @TypeConverter
    fun toRelevance(value: String): Relevance {
        return Relevance.valueOf(value)
    }
}