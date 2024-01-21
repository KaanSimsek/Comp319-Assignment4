package com.example.comp319_assignment4

import android.app.Application
import com.example.comp319_assignment4.data.AppContainer
import com.example.comp319_assignment4.data.AppDataContainer

class PhonebookApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}