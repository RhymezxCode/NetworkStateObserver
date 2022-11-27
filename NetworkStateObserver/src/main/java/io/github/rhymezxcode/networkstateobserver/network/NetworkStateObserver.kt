package io.github.rhymezxcode.networkstateobserver.network

import android.app.Activity
import androidx.lifecycle.LifecycleOwner

class NetworkStateObserver(
    private val viewLifecycleOwner: LifecycleOwner?,
    private val activity: Activity?,
    reachUrl: String?,
    serverUrl: String?
) {
    private lateinit var checkNetworkConnection: CheckNetworkConnection
    private var reach = Reachability(reachUrl ?: "www.google.com", serverUrl)
    private var context = activity?.applicationContext
    fun callNetworkConnection(): Boolean {
        checkNetworkConnection = CheckNetworkConnection(activity?.application!!)
        var state = false
        checkNetworkConnection.observe(viewLifecycleOwner!!) { isConnected ->
            state = if (isConnected) {
                when {
                    reach.hasServerConnected(context!!) ->
                        true

                    reach.hasInternetConnected(context!!) ->
                        false

                    else -> false
                }

            } else {
                //check for lost connection
                false
            }
        }
        return state
    }

    private constructor(builder: Builder) : this(
        builder.viewLifecycleOwner,
        builder.activity,
        builder.reachUrl,
        builder.serverUrl
    )

    class Builder {
        var viewLifecycleOwner: LifecycleOwner? = null
            private set

        var activity: Activity? = null
            private set

        var reachUrl: String? = null
            private set

        var serverUrl: String? = null
            private set

        fun lifecycleOwner(viewLifecycleOwner: LifecycleOwner) =
            apply { this.viewLifecycleOwner = viewLifecycleOwner }

        fun setServerURl(serverUrl: String) =
            apply { this.serverUrl = serverUrl }

        fun setReachURl(reachUrl: String) =
            apply { this.reachUrl = reachUrl }

        fun activity(activity: Activity) = apply { this.activity = activity }

        fun build() = NetworkStateObserver(this)
    }

}