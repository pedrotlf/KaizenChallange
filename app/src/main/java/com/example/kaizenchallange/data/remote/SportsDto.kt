package com.example.kaizenchallange.data.remote

data class SportDto(
    val i: String?,
    val d: String?,
    val e: List<EventDto>
)

data class EventDto(
    val i: String?,
    val si: String?,
    val sh: String?,
    val d: String?,
    val tt: Int?
)