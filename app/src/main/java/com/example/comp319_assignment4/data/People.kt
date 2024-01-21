package com.example.comp319_assignment4.data

import androidx.room.PrimaryKey

data class People(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val email: String?
)