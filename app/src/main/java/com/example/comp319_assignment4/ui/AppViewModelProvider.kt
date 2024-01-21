package com.example.comp319_assignment4.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.comp319_assignment4.ui.home.HomeViewModel
import com.example.comp319_assignment4.ui.people.PeopleEditViewModel
import com.example.comp319_assignment4.PhonebookApplication
import com.example.comp319_assignment4.ui.people.PeopleDetailsViewModel
import com.example.comp319_assignment4.ui.people.PeopleEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            PeopleEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.peopleRepository
            )
        }
        // Initializer for ItemEntryViewModel
        initializer {
            PeopleEntryViewModel(inventoryApplication().container.peopleRepository)
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            PeopleDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.peopleRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(inventoryApplication().container.peopleRepository)
        }
    }
}

fun CreationExtras.inventoryApplication(): PhonebookApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PhonebookApplication)