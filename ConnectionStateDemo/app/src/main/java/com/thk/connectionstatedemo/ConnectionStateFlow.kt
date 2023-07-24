package com.thk.connectionstatedemo

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.flow.MutableStateFlow

class ConnectionStateFlow(
    private val manager: ConnectivityManager
) : MutableStateFlow<Boolean> by MutableStateFlow(false) {

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            value = true
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            value = false
        }

        override fun onUnavailable() {
            super.onUnavailable()
            value = false
        }
    }

    // TODO: lifecycle에 맞춰 콜백 등록/해제 방법?

    fun registerCallback() {
        manager.registerDefaultNetworkCallback(callback)
    }

    fun unregisterCallback() {
        manager.unregisterNetworkCallback(callback)
    }
}

