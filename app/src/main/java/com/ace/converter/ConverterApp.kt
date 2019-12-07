package com.ace.converter

import android.app.Application
import com.facebook.stetho.Stetho

class ConverterApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

}