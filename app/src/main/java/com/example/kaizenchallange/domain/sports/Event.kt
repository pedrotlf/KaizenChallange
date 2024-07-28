package com.example.kaizenchallange.domain.sports

data class Event(
    val id: String,
    val sportId: String,
    val eventName: String,
    val startTime: Int,
    val competitor1: String?,
    val competitor2: String?
)
