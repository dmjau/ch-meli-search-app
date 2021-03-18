package com.cursoandroid.melisearchapp.data.common

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.cursoandroid.melisearchapp.data.ApiServiceMeli

/*
 * Verificamoss si se realiza correctamente la conexión a internet.
 */
class ConnectivityCheck {
    companion object {
        fun verifyConnection(conManager: ConnectivityManager): Boolean {
            val networkInfo: NetworkInfo? = conManager.activeNetworkInfo
            val connectionStatus = networkInfo?.isConnected == true
            if (!connectionStatus) {
                ApiServiceMeli.instance = null
            }
            return connectionStatus
        }
    }
}