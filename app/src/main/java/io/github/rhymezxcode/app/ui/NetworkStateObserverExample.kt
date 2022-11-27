package io.github.rhymezxcode.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.rhymezxcode.app.R
import io.github.rhymezxcode.app.util.showToast
import io.github.rhymezxcode.networkstateobserver.network.NetworkStateObserver

class NetworkStateObserverExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val network = NetworkStateObserver.Builder()
            .activity(this@NetworkStateObserverExample)
            .lifecycleOwner(this)
            .setReachURl("www.google.com")
            .setServerURl("www.github.com")
            .build()

        when(network.callNetworkConnection()){
            true -> showToast(this, "Network is restored")
            false -> showToast(this, "Network is lost")
            else -> {}
        }

    }
}