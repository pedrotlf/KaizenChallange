package com.example.kaizenchallange.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EventsGrid(
    eventList: List<Event>
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 4
    ) {
        eventList.forEach { event ->
            EventItem(event = event)
        }
    }
}

@Composable
fun EventItem(
    event: Event
) {
    var timeLeft by remember {
        mutableIntStateOf((event.startTime - System.currentTimeMillis() / 1000).toInt())
    }
    
    LaunchedEffect(key1 = timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft = (event.startTime - System.currentTimeMillis() / 1000).toInt()
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
        if (event.competitor1.isNullOrBlank().not()) {
            Text(text = event.competitor1.orEmpty(), textAlign = TextAlign.Center)
            Text(text = "vs", color = Color.Red, textAlign = TextAlign.Center)
            Text(text = event.competitor2.orEmpty(), textAlign = TextAlign.Center)
        } else {
            Text(text = event.eventName, textAlign = TextAlign.Center)
        }
    }
}