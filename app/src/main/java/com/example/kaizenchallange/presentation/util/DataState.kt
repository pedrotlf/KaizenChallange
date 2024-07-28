package com.example.kaizenchallange.presentation.util

data class DataState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)