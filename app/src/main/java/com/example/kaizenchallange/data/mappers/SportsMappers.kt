package com.example.kaizenchallange.data.mappers

import com.example.kaizenchallange.data.remote.EventDto
import com.example.kaizenchallange.data.remote.SportDto
import com.example.kaizenchallange.domain.sports.Event
import com.example.kaizenchallange.domain.sports.Sport

fun List<SportDto>.toSportsList(): List<Sport> {
    return map { sportDto ->
        Sport(
            id = sportDto.i.orEmpty(),
            name = sportDto.d.orEmpty(),
            events = sportDto.e.toEventsList()
        )
    }
}

fun List<EventDto>.toEventsList(): List<Event> {
    return map { eventDto ->
        val competitors = eventDto.d?.parseCompetitors()
        Event(
            id = eventDto.i.orEmpty(),
            eventName = eventDto.d.orEmpty(),
            sportId = eventDto.si.orEmpty(),
            startTime = eventDto.tt ?: 0,
            competitor1 = competitors?.first,
            competitor2 = competitors?.second
        )
    }
}

private fun String.parseCompetitors(): Pair<String, String>? {
    if (contains(" - ").not()) return null

    val splittedString = split(" - ")

    return splittedString.first() to splittedString.last()
}