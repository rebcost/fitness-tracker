package com.fcrysthian.myfitnesstraker

import android.app.Application
import com.fcrysthian.myfitnesstraker.model.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}

