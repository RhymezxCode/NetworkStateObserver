package io.github.rhymezxcode.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.rhymezxcode.networkstateobserver.network.NetworkStateObserver

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val network = NetworkStateObserver
            .Builder()
            .activity(this)
            .lifecycleOwner(this)
            .build()

    }
}