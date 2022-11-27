@file:Suppress("DEPRECATION")

package io.github.rhymezxcode.networkstateobserver.network

import android.content.Context
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MyReachability(private val reachUrl: String, private val serverUrl: String) {

    fun hasInternetConnected(context: Context): Boolean {
        if (CheckConnectivity.isNetworkAvailable(context)) {
            try {
                val connection = URL(reachUrl).openConnection() as
                        HttpURLConnection
                connection.setRequestProperty("User-Agent", "Test")
                connection.setRequestProperty("Connection", "close")
                connection.connectTimeout = 1500 // configurable
                connection.connect()
                Log.d(
                    "NetworkChecker", "hasInternetConnected: " +
                            "${(connection.responseCode == 200)}"
                )
                return (connection.responseCode == 200)
            } catch (e: IOException) {
                Log.e("NetworkChecker", "Error checking internet connection", e)
            }
        } else {
            Log.w("NetworkChecker", "No network available!")
        }
        Log.d("NetworkChecker", "hasInternetConnected: false")
        return false
    }

    fun hasServerConnected(context: Context): Boolean {
        if (CheckConnectivity.isNetworkAvailable(context)) {
            try {
                val connection = URL(serverUrl).openConnection() as HttpURLConnection
                connection.setRequestProperty("User-Agent", "Test")
                connection.setRequestProperty("Connection", "close")
                connection.connectTimeout = 1500 // configurable
                connection.connect()
                Log.d(
                    "NetworkChecker", "hasServerConnected: " +
                            "${(connection.responseCode == 200)}"
                )
                return (connection.responseCode == 200)
            } catch (e: IOException) {
                Log.e("NetworkChecker", "Error checking internet connection", e)
            }
        } else {
            Log.w("NetworkChecker", "Server is unavailable!")
        }
        Log.d("NetworkChecker", "hasServerConnected: false")
        return false
    }
}