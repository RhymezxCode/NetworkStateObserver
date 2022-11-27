@file:Suppress("DEPRECATION")

package io.github.rhymezxcode.networkstateobserver.network

import android.content.Context
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class Reachability(private val reachUrl: String? = null, private val serverUrl: String? = null) {
    fun hasInternetConnected(context: Context): Boolean {
        var state = false
        if (reachUrl.isNullOrEmpty()) {
            state = false
            Log.d(
                Companion.TAG, "No reach Url set, it should be www.google.com.ng"
            )
        } else {
            if (CheckConnectivity.isNetworkAvailable(context)) {
                try {
                    val connection = URL(reachUrl).openConnection() as
                            HttpURLConnection
                    connection.setRequestProperty("User-Agent", "Test")
                    connection.setRequestProperty("Connection", "close")
                    connection.connectTimeout = 1500 // configurable
                    connection.connect()
                    Log.d(
                        TAG, "hasInternetConnected: " +
                                "${(connection.responseCode == 200)}"
                    )
                    state = (connection.responseCode == 200)
                } catch (e: IOException) {
                    Log.e(TAG, "Error checking internet connection", e)
                }
            } else {
                Log.w(TAG, "No network available!")
            }
            Log.d(TAG, "hasInternetConnected: false")
        }
        return state
    }

    fun hasServerConnected(context: Context): Boolean {
        var state = false
        if (serverUrl.isNullOrEmpty()) {
            state = true
            Log.d(
                TAG, "it is not compulsory to set your server url"
            )
        } else {
            if (CheckConnectivity.isNetworkAvailable(context)) {
                try {
                    val connection = URL(serverUrl).openConnection() as HttpURLConnection
                    connection.setRequestProperty("User-Agent", "Test")
                    connection.setRequestProperty("Connection", "close")
                    connection.connectTimeout = 1500 // configurable
                    connection.connect()
                    Log.d(
                        TAG, "hasServerConnected: " +
                                "${(connection.responseCode == 200)}"
                    )
                    state = (connection.responseCode == 200)
                } catch (e: IOException) {
                    Log.e(TAG, "Error checking internet connection", e)
                }
            } else {
                Log.w(TAG, "Server is unavailable!")
            }
            Log.d(TAG, "hasServerConnected: false")
        }

        return state
    }

    companion object {
        const val TAG = "NetworkStateObserver"
    }
}