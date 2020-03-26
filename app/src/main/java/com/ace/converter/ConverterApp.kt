package com.ace.converter

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho


class ConverterApp : Application() {

    companion object {
        lateinit var cx: Context
        fun getAppContext(): Context? {
            return cx
        }
    }


    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        cx = applicationContext
    }
}