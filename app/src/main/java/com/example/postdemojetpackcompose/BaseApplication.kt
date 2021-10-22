package com.example.postdemojetpackcompose

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

lateinit var provideAppContext :Context
@HiltAndroidApp
class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        provideAppContext = this
    }
}

