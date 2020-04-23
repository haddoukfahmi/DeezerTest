package com.deezer.test.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData


class CheckConnectivity(private val mContext: Context) : LiveData<Boolean?>() {

    companion object {
        private val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    }

    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var networkReceiver: NetworkReceiver? = null
    private val connectivityManager: ConnectivityManager?


    init {
        connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = NetworkCallback(this)
        } else {
            networkReceiver = NetworkReceiver()
        }
    }

    override fun onActive() {
        super.onActive()
        updateConnection()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager!!.registerDefaultNetworkCallback(networkCallback)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
            connectivityManager!!.registerNetworkCallback(networkRequest, networkCallback)
        } else {
            mContext.registerReceiver(
                networkReceiver,
                IntentFilter(CONNECTIVITY_ACTION)
            )
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager!!.unregisterNetworkCallback(networkCallback)
        } else {
            mContext.unregisterReceiver(networkReceiver)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    internal inner class NetworkCallback(private val mConnectionStateMonitor: CheckConnectivity) :
        ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            if (network != null) {
                mConnectionStateMonitor.postValue(true)
            }
        }

        override fun onLost(network: Network) {
            mConnectionStateMonitor.postValue(false)
        }

    }

    private fun updateConnection() {
        if (connectivityManager != null) {
            val activeNetwork = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
                postValue(true)
            } else {
                postValue(false)
            }
        }
    }

    internal inner class NetworkReceiver : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            if (intent.action == CONNECTIVITY_ACTION) {
                updateConnection()
            }
        }
    }

}