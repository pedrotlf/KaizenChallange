package com.example.kaizenchallange.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaizenchallange.domain.repository.KaizenRepository
import com.example.kaizenchallange.domain.sports.Sport
import com.example.kaizenchallange.domain.util.Resource
import com.example.kaizenchallange.presentation.util.DataState
import kotlinx.coroutines.launch

class SportsViewModel(
    private val repository: KaizenRepository
): ViewModel() {

    var sportsState by mutableStateOf(DataState<List<Sport>>())
        private set

    init {
        loadSports()
    }

    fun loadSports() = viewModelScope.launch {
        sportsState = sportsState.copy(
            isLoading = true,
            error = null
        )
        when(val result = repository.getSportsList()) {
            is Resource.Success -> {
                sportsState = sportsState.copy(
                    data = result.data,
                    isLoading = false,
                    error = null
                )
            }
            is Resource.Error -> {
                sportsState = sportsState.copy(
                    data = null,
                    isLoading = false,
                    error = result.message
                )
            }
        }
    }
}