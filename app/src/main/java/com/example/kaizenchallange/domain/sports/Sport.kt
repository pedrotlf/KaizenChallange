package com.example.kaizenchallange.domain.sports

data class Sport(
    val id: String,
    val name: String,
    val events: List<Event>
)