package com.example.vkcup

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App:Application() {
    @Override
    override fun onCreate() {
        super.onCreate()
        val applicationScope = CoroutineScope(SupervisorJob())
        val database by lazy { AppDatabase.getDatabase(this,applicationScope) }
    }
}