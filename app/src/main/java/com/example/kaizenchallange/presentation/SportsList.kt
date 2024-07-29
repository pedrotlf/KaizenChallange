package com.example.kaizenchallange.presentation

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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.kaizenchallange.presentation.util.snapshotStateListSaver

@Composable
fun SportsList(
    viewModel: SportsViewModel,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val state by viewModel.sportsState.collectAsState()
    val sportsFavoriteFilter by viewModel.sportsFavoriteFilter.collectAsState()
    val isExpandedMap = rememberSaveable(saver = snapshotStateListSaver()) {
        mutableStateListOf()
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
                isExpanded = isExpandedMap.contains(sport.id),
                isFavoriteFiltering = sportsFavoriteFilter.contains(sport.id),
                onClickExpanded = { willExpand ->
                    if (willExpand)
                        isExpandedMap.add(sport.id)
                    else
                        isExpandedMap.remove(sport.id)
                },
                onFavoriteFilterToggle = { toggle ->
                    viewModel.toggleSportFavoriteFilter(sport.id, toggle)
                },
                onFavoriteStarClick = { eventId, isFavorite ->
                    if (isFavorite) {
                        viewModel.addFavoriteEvent(eventId)
                    } else {
                        viewModel.removeFavoriteEvent(eventId)
                    }
                }
            )
        }

        SportsListError(state)
    }
}

@Composable
private fun SportsListError(state: DataState<List<Sport>>) {
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
    isFavoriteFiltering: Boolean,
    onClickExpanded: (Boolean) -> Unit,
    onFavoriteFilterToggle: (Boolean) -> Unit,
    onFavoriteStarClick: (String, Boolean) -> Unit
) {
    val transition = updateTransition(targetState = isExpanded, label = "transition")

    val iconRotationDeg by transition.animateFloat(label = "icon change") { state ->
        if (state) {
            0f
        } else {
            180f
        }
    }

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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = sport.name)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = isFavoriteFiltering,
                        onCheckedChange = {
                            onFavoriteFilterToggle(it)
                        },
                        thumbContent = {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                            )
                        }
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(iconRotationDeg)
                            .clickable {
                                onClickExpanded(isExpanded.not())
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExpandableContent(
                isExpanded = isExpanded
            ) {
                EventsGrid(
                    eventList = sport.events,
                    onStarClick = { eventId, isFavorite ->
                        onFavoriteStarClick(eventId, isFavorite)
                    }
                )
            }
        }
    }
}