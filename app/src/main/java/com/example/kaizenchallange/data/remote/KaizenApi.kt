package com.example.kaizenchallange.data.remote

import retrofit2.http.GET

interface KaizenApi {

    @GET("sports")
    suspend fun getSportsList(): List<SportDto>
}