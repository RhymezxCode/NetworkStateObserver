package io.github.rhymezxcode.app.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.rhymezxcode.app.R
import io.github.rhymezxcode.app.util.showToast
import io.github.rhymezxcode.networkstateobserver.network.NetworkObserver
import io.github.rhymezxcode.networkstateobserver.network.NetworkStateObserver
import io.github.rhymezxcode.networkstateobserver.network.Reachability
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import java.io.IOException


class NetworkStateObserverExample : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val network = NetworkStateObserver.Builder()
            .activity(activity = this@NetworkStateObserverExample)
            .build()

        lifecycleScope.launch {
            network.callNetworkConnectionFlow()
                .observe()
                .collect {
                    when (it) {
                        NetworkObserver.Status.Available -> {
                            lifecycleScope.launch {
                                when {
                                    Reachability.hasServerConnectedFlow(
                                        context = this@NetworkStateObserverExample,
                                        serverUrl = "https://www.github.com"
                                    ).retryWhen { cause, attempt ->
                                        if (cause is IOException && attempt < 3) {
                                            delay(2000)
                                            return@retryWhen true
                                        } else {
                                            return@retryWhen false
                                        }
                                    }.buffer().first() -> lifecycleScope.launch {
                                        showToast(
                                            this@NetworkStateObserverExample,
                                            "Server url works"
                                        )
                                    }

                                    Reachability.hasInternetConnectedFlow(
                                        context = this@NetworkStateObserverExample
                                    ).retryWhen { cause, attempt ->
                                        if (cause is IOException && attempt < 3) {
                                            delay(2000)
                                            return@retryWhen true
                                        } else {
                                            return@retryWhen false
                                        }
                                    }.buffer().first() -> lifecycleScope.launch {
                                        showToast(
                                            this@NetworkStateObserverExample,
                                            "Network restored"
                                        )
                                    }

                                    else -> lifecycleScope.launch {
                                        showToast(
                                            this@NetworkStateObserverExample,
                                            "Network is lost or issues with server"
                                        )
                                    }
                                }
                            }
                        }

                        NetworkObserver.Status.Unavailable -> {
                            showToast(
                                this@NetworkStateObserverExample,
                                "Network is unavailable!"
                            )
                        }

                        NetworkObserver.Status.Losing -> {
                            showToast(
                                this@NetworkStateObserverExample,
                                "You are losing your network!"
                            )
                        }

                        NetworkObserver.Status.Lost -> {
                            showToast(
                                this@NetworkStateObserverExample,
                                "Network is lost!"
                            )
                        }
                    }
                }
        }

//        network.callNetworkConnection().observe(this) { isConnected ->
//            lifecycleScope.launch(Dispatchers.IO) {
//                if (isConnected) {
//                    when {
//                        Reachability.hasServerConnected(
//                            context = this@NetworkStateObserverExample,
//                            serverUrl = "https://www.github.com"
//                        ) -> lifecycleScope.launchWhenStarted {
//                            showToast(
//                                this@NetworkStateObserverExample,
//                                "Server url works"
//                            )
//                        }
//
//                        Reachability.hasInternetConnected(
//                            context = this@NetworkStateObserverExample
//                        ) -> lifecycleScope.launchWhenStarted {
//                            showToast(
//                                this@NetworkStateObserverExample,
//                                "Network restored"
//                            )
//                        }
//
//                        else -> lifecycleScope.launchWhenStarted {
//                            showToast(
//                                this@NetworkStateObserverExample,
//                                "Network is lost or issues with server"
//                            )
//                        }
//                    }
//                } else {
//                    //check for lost connection
//                    lifecycleScope.launchWhenStarted {
//                        showToast(
//                            this@NetworkStateObserverExample,
//                            "No Network connection"
//                        )
//                    }
//                }
//
//            }
//        }
    }


}