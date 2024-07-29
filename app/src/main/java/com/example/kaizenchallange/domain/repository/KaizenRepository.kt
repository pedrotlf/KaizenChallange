package com.example.kaizenchallange.domain.repository

import com.example.kaizenchallange.domain.sports.Sport
import com.example.kaizenchallange.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface KaizenRepository {

    suspend fun getSportsList(): Resource<List<Sport>>

    fun getFavoriteEventsList(): Flow<List<String>>

    suspend fun addFavoriteEvent(eventId: String)

    suspend fun removeFavoriteEvent(eventId: String)
}