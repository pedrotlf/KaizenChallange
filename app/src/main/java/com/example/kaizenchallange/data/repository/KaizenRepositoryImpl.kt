package com.example.kaizenchallange.data.repository

import com.example.kaizenchallange.data.local.FavoriteEventTable
import com.example.kaizenchallange.data.local.FavoritesDao
import com.example.kaizenchallange.data.mappers.toSportsList
import com.example.kaizenchallange.data.remote.KaizenApi
import com.example.kaizenchallange.domain.repository.KaizenRepository
import com.example.kaizenchallange.domain.sports.Sport
import com.example.kaizenchallange.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class KaizenRepositoryImpl(
    private val api: KaizenApi,
    private val favoritesDao: FavoritesDao
): KaizenRepository {

    override suspend fun getSportsList(): Resource<List<Sport>> {
        return try {
            Resource.Success(
                data = api.getSportsList().toSportsList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown error.")
        }
    }

    override fun getFavoriteEventsList(): Flow<List<String>> {
        return favoritesDao.getFavoriteEvents()
    }

    override suspend fun addFavoriteEvent(eventId: String) {
        favoritesDao.insertFavoriteEvent(FavoriteEventTable(eventId = eventId))
    }

    override suspend fun removeFavoriteEvent(eventId: String) {
        favoritesDao.deleteFavoriteEvent(eventId)
    }
}