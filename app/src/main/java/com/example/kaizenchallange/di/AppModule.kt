package com.example.kaizenchallange.di

import android.content.Context
import androidx.room.Room
import com.example.kaizenchallange.data.local.FavoritesDatabase
import com.example.kaizenchallange.data.remote.KaizenApi
import com.example.kaizenchallange.data.repository.KaizenRepositoryImpl
import com.example.kaizenchallange.domain.repository.KaizenRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

interface AppModule {
    val kaizenApi: KaizenApi
    val kaizenRepository: KaizenRepository
    val favoritesDatabase: FavoritesDatabase
}

class AppModuleImpl(
    private val appContext: Context
): AppModule {
    override val kaizenApi: KaizenApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://618d3aa7fe09aa001744060a.mockapi.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
    override val kaizenRepository: KaizenRepository by lazy {
        KaizenRepositoryImpl(kaizenApi, favoritesDatabase.dao)
    }
    override val favoritesDatabase: FavoritesDatabase by lazy {
        Room.databaseBuilder(
            appContext,
            FavoritesDatabase::class.java,
            "favorites.db"
        ).build()
    }
}