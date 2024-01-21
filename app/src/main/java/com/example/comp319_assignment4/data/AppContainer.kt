package com.example.comp319_assignment4.data

import android.content.Context

interface AppContainer {
    val peopleRepository: PeopleRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val peopleRepository: PeopleRepository by lazy {
        OfflinePeopleRepository(PeopleDatabase.getDatabase(context).peopleDao())
    }
}