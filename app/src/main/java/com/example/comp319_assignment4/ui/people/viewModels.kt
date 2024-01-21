package com.example.comp319_assignment4.ui.people

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comp319_assignment4.data.People
import com.example.comp319_assignment4.data.PeopleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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


class PeopleEntryViewModel(private val peopleRepository: PeopleRepository) : ViewModel() {


    var peopleUiState by mutableStateOf(PeopleUiState())
        private set

    fun updateUiState(peopleDetails: PeopleDetails) {
        peopleUiState =
            PeopleUiState(peopleDetails = peopleDetails, isEntryValid = validateInput(peopleDetails))
    }

    suspend fun savePeople() {
        if (validateInput()) {
            peopleRepository.insertPeople(peopleUiState.peopleDetails.toItem())
        }
    }

    private fun validateInput(uiState: PeopleDetails = peopleUiState.peopleDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && surname.isNotBlank() && phoneNumber.isNotBlank()
        }
    }
}


data class PeopleUiState(
    val peopleDetails: PeopleDetails = PeopleDetails(),
    val isEntryValid: Boolean = false
)

data class PeopleDetails(
    val id: Int = 0,
    val name: String = "",
    val surname: String = "",
    val phoneNumber: String = "",
    val email: String? = ""
)

fun PeopleDetails.toItem(): People = People(
    id = id,
    name = name,
    surname = surname,
    phoneNumber = phoneNumber,
    email = email
)

fun People.toPeopleUiState(isEntryValid: Boolean = false): PeopleUiState = PeopleUiState(
    peopleDetails = this.toPeopleDetails(),
    isEntryValid = isEntryValid
)

fun People.toPeopleDetails(): PeopleDetails = PeopleDetails(
    id = id,
    name = name,
    surname = surname,
    phoneNumber = phoneNumber,
    email = email
)