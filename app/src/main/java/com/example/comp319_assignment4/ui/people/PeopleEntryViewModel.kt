package com.example.comp319_assignment4.ui.people

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.comp319_assignment4.data.People
import com.example.comp319_assignment4.data.PeopleRepository


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
            name.isNotBlank() && surname.isNotBlank() && phoneNumber.isNotBlank() && email!!.isNotBlank()
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