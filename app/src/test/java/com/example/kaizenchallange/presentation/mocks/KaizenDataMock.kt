package com.example.kaizenchallange.presentation.mocks

import com.example.kaizenchallange.domain.sports.Event
import com.example.kaizenchallange.domain.sports.Sport

fun getKaizenBigDataMock(
    hasFavorite: Boolean = false
): List<Sport> {
    val currentTime = (System.currentTimeMillis() / 1000).toInt()
    return listOf(
        Sport(
            id = "FOOT",
            name = "Football",
            events = listOf(
                Event(
                    id = "FOOT Greece-Spain",
                    sportId = "",
                    eventName = "Greece-Spain",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "FOOT Brazil-Spain",
                    sportId = "",
                    eventName = "Brazil-Spain",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null,
                    isFavorite = hasFavorite
                ),
                Event(
                    id = "FOOT Greece-Brazil",
                    sportId = "",
                    eventName = "Greece-Brazil",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "FOOT Greece-Italy",
                    sportId = "",
                    eventName = "Greece-Italy",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "FOOT Italy-Spain",
                    sportId = "",
                    eventName = "Italy-Spain",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null,
                    isFavorite = hasFavorite
                )
            )
        ),
        Sport(
            id = "TENNIS",
            name = "Tennis",
            events = listOf(
                Event(
                    id = "TENNIS Greece-Spain",
                    sportId = "",
                    eventName = "Greece-Spain",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS Brazil-Spain",
                    sportId = "",
                    eventName = "Brazil-Spain",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS Greece-Brazil",
                    sportId = "",
                    eventName = "Greece-Brazil",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS Greece-Italy",
                    sportId = "",
                    eventName = "Greece-Italy",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS Italy-Brazil",
                    sportId = "",
                    eventName = "Italy-Brazil",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS Italy-USA",
                    sportId = "",
                    eventName = "Italy-USA",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS USA-Spain",
                    sportId = "",
                    eventName = "USA-Spain",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS USA-Brazil",
                    sportId = "",
                    eventName = "USA-Brazil",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS USA-Greece",
                    sportId = "",
                    eventName = "USA-Greece",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS Italy-Japan",
                    sportId = "",
                    eventName = "Italy-Japan",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS Japan-Spain",
                    sportId = "",
                    eventName = "Japan-Spain",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS Japan-Brazil",
                    sportId = "",
                    eventName = "Japan-Brazil",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS Japan-Greece",
                    sportId = "",
                    eventName = "Japan-Greece",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS France-Greece",
                    sportId = "",
                    eventName = "France-Greece",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS Italy-France",
                    sportId = "",
                    eventName = "Italy-France",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "TENNIS France-Spain",
                    sportId = "",
                    eventName = "France-Spain",
                    startTime = currentTime + 1500,
                    competitor1 = null,
                    competitor2 = null
                )
            )
        ),
        Sport(
            id = "BASK",
            name = "Basketball",
            events = listOf(
                Event(
                    id = "BASK Greece-Spain",
                    sportId = "",
                    eventName = "Greece-Spain",
                    startTime = currentTime + 3,
                    competitor1 = null,
                    competitor2 = null
                ),
                Event(
                    id = "BASK Brazil-Spain",
                    sportId = "",
                    eventName = "Brazil-Spain",
                    startTime = currentTime + 3000,
                    competitor1 = null,
                    competitor2 = null
                )
            )
        ),
        Sport(
            id = "ESPO",
            name = "Esports",
            events = listOf(
                Event(
                    id = "ESPO Greece-Spain",
                    sportId = "",
                    eventName = "Greece-Spain",
                    startTime = currentTime + 2000,
                    competitor1 = null,
                    competitor2 = null,
                    isFavorite = hasFavorite
                )
            )
        )
    )
}