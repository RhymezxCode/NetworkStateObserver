package io.github.rhymezxcode.networkstateobserver.network

import android.app.Activity
import androidx.lifecycle.LifecycleOwner

class NetworkStateObserver(private val viewLifecycleOwner: LifecycleOwner,
                           private val activity: Activity) {
    private lateinit var checkNetworkConnection: CheckNetworkConnection
    private var reach = MyReachability("www.google.com", "www.github.com")
    private var context = activity.applicationContext
    private fun callNetworkConnection() {
        checkNetworkConnection = CheckNetworkConnection(activity.application)
        checkNetworkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                val loader = Thread {
                    when {
                        reach.hasServerConnected(context) ->
                            activity.runOnUiThread {

                            }
                        reach.hasInternetConnected(context) ->
                            activity.runOnUiThread {
                                //check for weak connection
                            }
                        else -> activity.runOnUiThread {
                            //check for lost connection
                        }
                    }
                }
                loader.start()

            } else {
                //check for lost connection
            }
        }

    }
}