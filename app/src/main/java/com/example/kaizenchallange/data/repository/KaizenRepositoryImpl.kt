package com.example.kaizenchallange.data.repository

import com.example.kaizenchallange.data.mappers.toSportsList
import com.example.kaizenchallange.data.remote.KaizenApi
import com.example.kaizenchallange.domain.repository.KaizenRepository
import com.example.kaizenchallange.domain.sports.Sport
import com.example.kaizenchallange.domain.util.Resource

class KaizenRepositoryImpl(
    private val api: KaizenApi
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
}