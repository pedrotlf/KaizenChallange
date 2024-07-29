package com.example.kaizenchallange.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaizenchallange.domain.repository.KaizenRepository
import com.example.kaizenchallange.domain.sports.Sport
import com.example.kaizenchallange.domain.util.Resource
import com.example.kaizenchallange.presentation.util.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SportsViewModel(
    private val repository: KaizenRepository
): ViewModel() {

    private val _sportsState = MutableStateFlow(DataState<List<Sport>>())

    private val _favoriteEvents = repository.getFavoriteEventsList()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _sportsFavoriteToggleList = MutableStateFlow((emptyList<String>()))
    /**
     * Returns a state flow of the sports which the user chose to filter only favorite events.
     */
    val sportsFavoriteToggleList: StateFlow<List<String>> = _sportsFavoriteToggleList

    /**
     * Returns a state flow of the sports list after updating every event with its corresponding
     * 'favorite' state restored from the local database.
     */
    private val _sportsUpdatedState = combine(_sportsState, _favoriteEvents) { state, favoriteEvents ->
        state.copy(
            data = state.data?.map { sport -> sport.copy(
                events = sport.events.map { event -> event.copy(
                    isFavorite = favoriteEvents.contains(event.id)
                ) }
            ) }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DataState())

    /**
     * Returns a state flow of the sports list but filtering out events that are not favorite for sports
     * which the user chose to filter only favorite events.
     */
    val filteredSportsState = combine(_sportsUpdatedState, _sportsFavoriteToggleList) { updatedState, favoritesFilter ->
        updatedState.copy(
            data = updatedState.data?.map { sport -> sport.copy(
                events = sport.events.filter {
                    if (favoritesFilter.contains(sport.id))
                        it.isFavorite
                    else
                        true
                }
            ) }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DataState())

    init {
        loadSports()
    }

    fun loadSports() = viewModelScope.launch {
        _sportsState.update { it.copy(
            isLoading = true,
            error = null
        ) }
        when(val result = repository.getSportsList()) {
            is Resource.Success -> {
                _sportsState.update { it.copy(
                    data = result.data,
                    isLoading = false,
                    error = null
                ) }
            }
            is Resource.Error -> {
                _sportsState.update { it.copy(
                    data = null,
                    isLoading = false,
                    error = result.message
                ) }
            }
        }
    }

    fun addFavoriteEvent(eventId: String) = viewModelScope.launch {
        repository.addFavoriteEvent(eventId)
    }

    fun removeFavoriteEvent(eventId: String) = viewModelScope.launch {
        repository.removeFavoriteEvent(eventId)
    }

    fun toggleSportFavoriteFilter(sportId: String, toggle: Boolean) {
        _sportsFavoriteToggleList.update { filterMap ->
            if (toggle) {
                filterMap + sportId
            } else {
                filterMap - sportId
            }
        }
    }
}