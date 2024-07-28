package com.example.kaizenchallange.presentation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kaizenchallange.domain.sports.Sport
import com.example.kaizenchallange.presentation.expandablecontent.ExpandableContent
import com.example.kaizenchallange.presentation.pullrefresh.PullToRefreshLazyColumn
import com.example.kaizenchallange.presentation.util.DataState
import com.example.kaizenchallange.presentation.util.snapshotStateMapSaver

@Composable
fun SportsList(
    viewModel: SportsViewModel,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val state = viewModel.sportsState
    val isExpandedMap = rememberSaveable(
        state.data,
        saver = snapshotStateMapSaver()
    ) {
        state.data?.map { sport -> sport.id to true }.orEmpty()
            .toMutableStateMap()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        PullToRefreshLazyColumn(
            items = state.data.orEmpty(),
            isRefreshing = state.isLoading,
            onRefresh = {
                viewModel.loadSports()
            },
            lazyListState = lazyListState
        ) { sport ->
            SportItem(
                sport = sport,
                isExpanded = isExpandedMap[sport.id] ?: true,
                onClickExpanded = {
                    isExpandedMap[sport.id] = (isExpandedMap[sport.id] ?: true).not()
                }
            )
        }

        ShowListError(state)
    }
}

@Composable
private fun ShowListError(state: DataState<List<Sport>>) {
    if (state.isLoading.not()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.data?.isEmpty() == true -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Currently there are no events to be shown."
                    )
                }

                state.data == null || state.error != null -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Something wrong happened. Try again later."
                    )
                }
            }
        }
    }
}

@Composable
fun SportItem(
    sport: Sport,
    isExpanded: Boolean,
    onClickExpanded: () -> Unit
) {
    val transition = updateTransition(targetState = isExpanded, label = "transition")

    val iconRotationDeg by transition.animateFloat(label = "icon change") { state ->
        if (state) {
            0f
        } else {
            180f
        }
    }
//    val color by transition.animateColor(label = "color change") { state ->
//        if (state) {
//            Color.Black.copy(.4f)
//        } else {
//            MaterialTheme.colorScheme.surface
//        }
//    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(.4f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = sport.name)
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(iconRotationDeg)
                        .clickable {
                            onClickExpanded()
                        }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExpandableContent(
                isExpanded = isExpanded
            ) {
                EventsGrid(sport.events)
            }
        }
    }
}