package com.ace.converter

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import com.ace.converter.network.BASE_URL
import java.net.InetAddress

object Utils {
    fun isNetworkConnected(): Boolean {
        val cm: ConnectivityManager =
                ConverterApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected //&& isServerAvailable()
    }


//    fun isServerAvailable(): Boolean {
//        return try {
//            InetAddress.getByName(Uri.parse(BASE_URL).host).hostAddress.isNotEmpty()
//        } catch (e: Exception) {
//            false
//        }
//    }
}