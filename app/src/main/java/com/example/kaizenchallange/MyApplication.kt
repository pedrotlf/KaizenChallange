package com.example.kaizenchallange

import android.app.Application
import com.example.kaizenchallange.di.AppModule
import com.example.kaizenchallange.di.AppModuleImpl

class MyApplication: Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}