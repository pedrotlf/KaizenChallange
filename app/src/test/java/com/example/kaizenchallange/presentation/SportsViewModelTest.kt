package com.example.kaizenchallange.presentation

import com.example.kaizenchallange.MainCoroutineRule
import com.example.kaizenchallange.presentation.mocks.FakeKaizenRepository
import com.example.kaizenchallange.presentation.mocks.getKaizenBigDataMock
import com.example.kaizenchallange.presentation.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SportsViewModelTest {

    @get:Rule
    val dispatcherRule = MainCoroutineRule()

    private lateinit var fakeKaizenRepository: FakeKaizenRepository
    private lateinit var viewmodel: SportsViewModel

    @Before
    fun setUp() {
        fakeKaizenRepository = FakeKaizenRepository()
        viewmodel = SportsViewModel(fakeKaizenRepository)
    }

    @Test
    fun `sportsFavoriteFilter update`() = runTest {
        assertEquals(emptyList<String>(), viewmodel.sportsFavoriteFilter.value)

        viewmodel.toggleSportFavoriteFilter("FOOT", true)
        viewmodel.toggleSportFavoriteFilter("TENNIS", true)

        var expected = listOf("FOOT", "TENNIS")

        assertEquals(expected, viewmodel.sportsFavoriteFilter.value)

        viewmodel.toggleSportFavoriteFilter("TENNIS", false)

        expected = listOf("FOOT")

        assertEquals(expected, viewmodel.sportsFavoriteFilter.value)
    }

    @Test
    fun `sportsState on load`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewmodel.sportsState.collect {}
        }

        val expected = DataState(getKaizenBigDataMock(), isLoading = false, error = null)

        assertEquals(expected, viewmodel.sportsState.value)

        collectJob.cancel()
    }

    @Test
    fun `sportsState after adding a favorite`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewmodel.sportsState.collect {}
        }

        val fullData = getKaizenBigDataMock()

        viewmodel.addFavoriteEvent("TENNIS Brazil-Spain")
        viewmodel.toggleSportFavoriteFilter("TENNIS", true)

        // Filtering out events from TENNIS sport that are not the "TENNIS Brazil-Spain" and
        // updating its 'isFavorite' value so we can use as our expected result.
        val expected = DataState(
            fullData.map { sport -> sport.copy(
                events = sport.events.filter { event ->
                    if (sport.id == "TENNIS")
                        event.id == "TENNIS Brazil-Spain"
                    else
                        true
                }.map { event -> event.copy(isFavorite = event.id == "TENNIS Brazil-Spain") }
            ) },
            isLoading = false,
            error = null
        )

        assertEquals(expected, viewmodel.sportsState.value)

        collectJob.cancel()
    }

    @Test
    fun `sportsState after removing a favorite`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewmodel.sportsState.collect {}
        }

        val fullData = getKaizenBigDataMock()

        viewmodel.addFavoriteEvent("TENNIS Brazil-Spain")
        viewmodel.toggleSportFavoriteFilter("TENNIS", true)

        // Filtering out events from TENNIS sport that are not the "TENNIS Brazil-Spain" and
        // updating its 'isFavorite' value so we can use as our expected result.
        var expected = fullData.map { sport -> sport.copy(
            events = sport.events.filter { event ->
                if (sport.id == "TENNIS")
                    event.id == "TENNIS Brazil-Spain"
                else
                    true
            }.map { event -> event.copy(isFavorite = event.id == "TENNIS Brazil-Spain") }
        ) }

        // First we secured that the filter works after adding a favorite
        assertEquals(expected, viewmodel.sportsState.value.data)

        // Then we remove the favorite
        viewmodel.removeFavoriteEvent("TENNIS Brazil-Spain")

        // Now we should filter out all events from TENNIS since it does not have any favorites
        // and its corresponding favorite filter is toggled.
        expected = fullData.map { sport -> sport.copy(
            events = sport.events.filter { event ->
                sport.id != "TENNIS"
            }
        ) }

        // New filter check
        assertEquals(expected, viewmodel.sportsState.value.data)

        collectJob.cancel()
    }

    @Test
    fun `sportsState after untoggling a favorite sport`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewmodel.sportsState.collect {}
        }

        val fullData = getKaizenBigDataMock()

        viewmodel.addFavoriteEvent("TENNIS Brazil-Spain")
        viewmodel.toggleSportFavoriteFilter("TENNIS", true)

        // Filtering out events from TENNIS sport that are not the "TENNIS Brazil-Spain" and
        // updating its 'isFavorite' value so we can use as our expected result.
        var expected = fullData.map { sport -> sport.copy(
            events = sport.events.filter { event ->
                if (sport.id == "TENNIS")
                    event.id == "TENNIS Brazil-Spain"
                else
                    true
            }.map { event -> event.copy(isFavorite = event.id == "TENNIS Brazil-Spain") }
        ) }

        // First we secured that the filter works after adding a favorite
        assertEquals(expected, viewmodel.sportsState.value.data)

        // Then we untoggle the favorite filter for TENNIS
        viewmodel.toggleSportFavoriteFilter("TENNIS", false)

        // With no favorite filter toggled, data should get back to normal, except by the
        // "TENNIS Brazil-Spain" 'isFavorite' field that should still be 'true'.
        expected = fullData.map { sport -> sport.copy(
            events = sport.events.map { event -> event.copy(
                isFavorite = event.id == "TENNIS Brazil-Spain"
            ) }
        ) }

        // New filter check
        assertEquals(expected, viewmodel.sportsState.value.data)

        collectJob.cancel()
    }
}