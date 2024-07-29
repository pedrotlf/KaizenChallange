package com.example.kaizenchallange.data.local

import androidx.room.Entity

@Entity(primaryKeys = ["id", "eventId"])
data class FavoriteEventTable(
    val id: Int = 0,
    val eventId: String
)
