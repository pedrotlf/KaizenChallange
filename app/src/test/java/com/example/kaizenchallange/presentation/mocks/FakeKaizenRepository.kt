package com.example.kaizenchallange.presentation.mocks

import com.example.kaizenchallange.domain.repository.KaizenRepository
import com.example.kaizenchallange.domain.sports.Sport
import com.example.kaizenchallange.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeKaizenRepository: KaizenRepository {

    private val favoriteEventsList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    override suspend fun getSportsList(): Resource<List<Sport>> {
        return Resource.Success(data = getKaizenBigDataMock())
    }

    override fun getFavoriteEventsList(): Flow<List<String>> {
        return favoriteEventsList
    }

    override suspend fun addFavoriteEvent(eventId: String) {
        favoriteEventsList.update {
            it + eventId
        }
    }

    override suspend fun removeFavoriteEvent(eventId: String) {
        favoriteEventsList.update {
            it - eventId
        }
    }
}