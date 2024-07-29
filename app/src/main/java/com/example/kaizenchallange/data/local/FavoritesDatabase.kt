package com.example.kaizenchallange.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteEventTable::class],
    version = 1
)
abstract class FavoritesDatabase: RoomDatabase() {

    abstract val dao: FavoritesDao
}