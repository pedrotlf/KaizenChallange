package com.example.kaizenchallange.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kaizenchallange.domain.sports.Event
import com.example.kaizenchallange.presentation.util.formatTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EventsGrid(
    eventList: List<Event>,
    onStarClick: (String, Boolean) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 4
    ) {
        eventList.forEach { event ->
            EventItem(
                event = event,
                onStarClick = {
                    onStarClick(event.id, event.isFavorite.not())
                }
            )
        }
    }
}

@Composable
fun EventItem(
    event: Event,
    onStarClick: () -> Unit
) {
    // The key is needed here. If we don't provide it, timeLeft will remember its last state even if
    // the event is changed, which happens when a filter is applied for example.
    var timeLeft by remember(key1 = event.id) {
        mutableIntStateOf((event.startTime - System.currentTimeMillis() / 1000).toInt())
    }
    
    LaunchedEffect(key1 = timeLeft) {
        // IO dispatcher works better in parallel than the Default dispatcher.
        // Since there's a lot of event items launching this coroutine at the same time, we've
        // decided to move them to the IO dispatcher.
        withContext(Dispatchers.IO) {
            if (timeLeft > 0) {
                delay(1000L)
                timeLeft = (event.startTime - System.currentTimeMillis() / 1000).toInt()
            }
        }
    }
    
    Column(
        modifier = Modifier.width(130.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (timeLeft > 0) {
            Text(text = timeLeft.formatTime(), color = Color.Gray)
        } else {
            Text(text = "Game Started", color = Color.DarkGray)
        }
        Icon(
            imageVector = Icons.Default.Star,
            tint = if (event.isFavorite) Color.Yellow else Color.DarkGray,
            contentDescription = null,
            modifier = Modifier.clickable {
                onStarClick()
            }
        )
        if (event.competitor1.isNullOrBlank().not()) {
            Text(text = event.competitor1.orEmpty(), textAlign = TextAlign.Center)
            Text(text = "vs", color = Color.Red, textAlign = TextAlign.Center)
            Text(text = event.competitor2.orEmpty(), textAlign = TextAlign.Center)
        } else {
            Text(text = event.eventName, textAlign = TextAlign.Center)
        }
    }
}