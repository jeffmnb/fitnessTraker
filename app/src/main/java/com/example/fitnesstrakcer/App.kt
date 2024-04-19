package com.example.fitnesstrakcer

import android.app.Application
import com.example.fitnesstrakcer.model.AppDatabase

// Application() -> usada para executar coisas logo após o app ser iniciado
class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}