package io.github.rhymezxcode.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.rhymezxcode.app.R
import io.github.rhymezxcode.app.util.showToast
import io.github.rhymezxcode.networkstateobserver.network.NetworkStateObserver
import io.github.rhymezxcode.networkstateobserver.network.Reachability
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NetworkStateObserverExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val network = NetworkStateObserver.Builder()
            .activity(activity = this@NetworkStateObserverExample)
            .build()

        network.callNetworkConnection().observe(this) { isConnected ->
            lifecycleScope.launch(Dispatchers.IO) {
                if (isConnected) {
                    when {
                        Reachability.hasServerConnected(
                            context = this@NetworkStateObserverExample,
                            serverUrl = "https://www.github.com"
                        ) -> lifecycleScope.launchWhenStarted {
                            showToast(
                                this@NetworkStateObserverExample,
                                "Server url works"
                            )
                        }

                        Reachability.hasInternetConnected(
                            context = this@NetworkStateObserverExample
                        ) -> lifecycleScope.launchWhenStarted {
                            showToast(
                                this@NetworkStateObserverExample,
                                "Network restored"
                            )
                        }

                        else -> lifecycleScope.launchWhenStarted {
                            showToast(
                                this@NetworkStateObserverExample,
                                "Network is lost or issues with server"
                            )
                        }
                    }
                } else {
                    //check for lost connection
                    lifecycleScope.launchWhenStarted {
                        showToast(
                            this@NetworkStateObserverExample,
                            "No Network connection"
                        )
                    }
                }

            }

        }
    }


}