package com.example.comp319_assignment4.ui.people

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comp319_assignment4.data.PeopleRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PeopleEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val peopleRepository: PeopleRepository
) : ViewModel() {

    var peopleUiState by mutableStateOf(PeopleUiState())
        private set

    private val peopleId: Int = checkNotNull(savedStateHandle[PeopleEditDestination.peopleIdArg])

    init {
        viewModelScope.launch {
            peopleUiState = peopleRepository.getPeopleStream(peopleId)
                .filterNotNull()
                .first()
                .toPeopleUiState(true)
        }
    }

    suspend fun updatePeople() {
        if (validateInput(peopleUiState.peopleDetails)) {
            peopleRepository.updatePeople(peopleUiState.peopleDetails.toItem())
        }
    }

    fun updateUiState(peopleDetails: PeopleDetails) {
        peopleUiState =
            PeopleUiState(peopleDetails = peopleDetails, isEntryValid = validateInput(peopleDetails))
    }

    private fun validateInput(uiState: PeopleDetails = peopleUiState.peopleDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && surname.isNotBlank() && phoneNumber.isNotBlank()
        }
    }
}
