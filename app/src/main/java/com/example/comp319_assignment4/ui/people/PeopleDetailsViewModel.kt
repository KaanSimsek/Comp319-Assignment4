package com.example.comp319_assignment4.ui.people

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comp319_assignment4.data.PeopleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PeopleDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: PeopleRepository,
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[PeopleDetailsDestination.itemIdArg])
    val uiState: StateFlow<PeopleDetailsUiState> =
        itemsRepository.getPeopleStream(itemId)
            .filterNotNull()
            .map {
                PeopleDetailsUiState(peopleDetails = it.toPeopleDetails())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PeopleDetailsUiState()
            )
    suspend fun deleteItem() {
        itemsRepository.deletePeople(uiState.value.peopleDetails.toItem())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class PeopleDetailsUiState(
    val peopleDetails: PeopleDetails = PeopleDetails()
)