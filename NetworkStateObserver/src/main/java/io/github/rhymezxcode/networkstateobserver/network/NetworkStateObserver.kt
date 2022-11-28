package io.github.rhymezxcode.networkstateobserver.network

import android.app.Activity

class NetworkStateObserver(
    private val activity: Activity?,
) {

    fun callNetworkConnection(): CheckNetworkConnection{
        return CheckNetworkConnection(activity?.application!!)
    }

    private constructor(builder: Builder) : this(
        builder.activity
    )

    class Builder {

        var activity: Activity? = null
            private set

        fun activity(activity: Activity) = apply { this.activity = activity }

        fun build() = NetworkStateObserver(this)
    }

}