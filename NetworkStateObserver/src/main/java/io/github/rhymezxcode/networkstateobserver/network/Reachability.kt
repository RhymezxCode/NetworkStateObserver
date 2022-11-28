package io.github.rhymezxcode.networkstateobserver.network

import android.content.Context
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object Reachability {
    fun hasInternetConnected(
        context: Context? = null,
        reachUrl: String? = null,
    ): Boolean {
        var state: Boolean
        if (CheckConnectivity.isNetworkAvailable(context)) {
            try {
                val connection = URL(reachUrl ?: "https://www.google.com")
                    .openConnection() as
                        HttpURLConnection
                connection.setRequestProperty("User-Agent", "Test")
                connection.setRequestProperty("Connection", "close")
                connection.connectTimeout = 1500 // configurable
                connection.connect()
                Log.d(
                    "NetworkStateObserver", "hasInternetConnected: " +
                            "${(connection.responseCode == 200)}"
                )
                state = (connection.responseCode == 200)
            } catch (e: IOException) {
                state = false
                Log.e(
                    "NetworkStateObserver",
                    "Error checking internet connection", e
                )
            }
        } else {
            state = false
            Log.w("NetworkStateObserver", "No network available!")
        }

        return state
    }

    fun hasServerConnected(
        context: Context? = null,
        serverUrl: String? = null
    ): Boolean {
        var state: Boolean
        if (CheckConnectivity.isNetworkAvailable(context)) {
            try {
                val connection = URL(serverUrl)
                    .openConnection() as
                        HttpURLConnection
                connection.connectTimeout = 1500 // configurable
                connection.connect()
                Log.d(
                    "NetworkStateObserver", "hasServerConnected: " +
                            "${(connection.responseCode == 200)}"
                )
                state = (connection.responseCode == 200)
            } catch (e: IOException) {
                state = false
                Log.e(
                    "NetworkStateObserver",
                    "Error checking your server connection", e
                )
            }
        } else {
            state = false
            Log.e(
                "NetworkStateObserver",
                "No, Internet connection to check server connection!",
            )
        }

        return state
    }

}