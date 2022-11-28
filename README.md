# NetworkStateObserver
A library that helps you check the state of your network, if it is either available, lost, unavailable and also check the reach-ability of your network when your server is either down or your ISP is connected but no data subscription. 

# Usage
# 1. first initialize the builder class:

        val network = NetworkStateObserver.Builder()
            .activity(activity = this@NetworkStateObserverExample)
            .build()

# 2. use the live-data method to determine your network state, and replace the code in the lifecycleScope.launchWhenStarted { ....your code here } to do what you want:

        network.callNetworkConnection().observe(this) { isConnected ->
            lifecycleScope.launch(Dispatchers.IO) {
                if (isConnected) {
                    when {
                        Reachability.hasServerConnected(
                            context = this@NetworkStateObserverExample,
                            serverUrl = "https://www.your-server-url.com"
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

# 3. you can check if your internet connection is stable only, if you don't have a server url: 

        network.callNetworkConnection().observe(this) { isConnected ->
            lifecycleScope.launch(Dispatchers.IO) {
                if (isConnected) {
                    when {

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
