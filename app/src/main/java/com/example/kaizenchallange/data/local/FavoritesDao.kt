package com.example.kaizenchallange.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteEvent(eventId: FavoriteEventTable)

    @Query("DELETE FROM favoriteeventtable WHERE eventId=:eventId")
    suspend fun deleteFavoriteEvent(eventId: String)

    @Query("SELECT eventId FROM favoriteeventtable")
    fun getFavoriteEvents(): Flow<List<String>>
}