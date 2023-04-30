package io.github.rhymezxcode.networkstateobserver.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.HttpURLConnection
import java.net.URL

object Reachability {
    fun hasInternetConnected(
        context: Context,
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
            } catch (e: Exception) {
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
        context: Context,
        serverUrl: String
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
            } catch (e: Exception) {
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

    fun hasInternetConnectedFlow(
        context: Context,
        reachUrl: String? = null,
    ): Flow<Boolean> = flow {
        if (CheckConnectivity.isNetworkAvailable(context)) {
            val connection = URL(reachUrl ?: "https://www.google.com")
                .openConnection() as
                    HttpURLConnection
            connection.connectTimeout = 1500 // configurable
            connection.connect()
            Log.d(
                "NetworkStateObserver", "hasConnected: " +
                        "${(connection.responseCode == 200)}"
            )
            emit((connection.responseCode == 200))
        } else {
            emit(false)
            Log.e(
                "NetworkStateObserver",
                "No, Internet connection to check connection!",
            )
        }
    }.catch { e ->
        emit(false)
        Log.e(
            "NetworkStateObserver",
            "Error checking your connection: \n" + e.localizedMessage
        )
    }.flowOn(Dispatchers.IO)


    fun hasServerConnectedFlow(
        context: Context,
        serverUrl: String
    ): Flow<Boolean> = flow {
        if (CheckConnectivity.isNetworkAvailable(context)) {
            val connection = URL(serverUrl)
                .openConnection() as
                    HttpURLConnection
            connection.connectTimeout = 1500 // configurable
            connection.connect()
            Log.d(
                "NetworkStateObserver", "hasServerConnected: " +
                        "${(connection.responseCode == 200)}"
            )
            emit((connection.responseCode == 200))
        } else {
            emit(false)
            Log.e(
                "NetworkStateObserver",
                "No, Internet connection to check your server connection!",
            )
        }
    }.catch { e ->
        emit(false)
        Log.e(
            "NetworkStateObserver",
            "Error checking your connection: \n" + e.localizedMessage
        )
    }.flowOn(Dispatchers.IO)

}