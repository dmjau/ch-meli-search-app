package com.cursoandroid.melisearchapp.common

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.cursoandroid.melisearchapp.data.ApiServiceMeli

//Check the internet connection.
class ConnectivityCheck {
    companion object {
        fun isOnline(conManager: ConnectivityManager): Boolean {
            val networkInfo: NetworkInfo? = conManager.activeNetworkInfo
            val isConnected = networkInfo?.isConnected == true
            if (!isConnected) {
                ApiServiceMeli.instance = null
            }
            return isConnected
        }
    }
}