package com.example.kaizenchallange.domain.repository

import com.example.kaizenchallange.domain.sports.Sport
import com.example.kaizenchallange.domain.util.Resource

interface KaizenRepository {

    suspend fun getSportsList(): Resource<List<Sport>>
}