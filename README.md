<div align="center">
<h1>NetworkStateObserver Android Library</h1>

<a href="https://android-arsenal.com/api?level=21" target="blank">
    <img src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" alt="NetworkStateObserver Android Library least API level" />
</a>
<a href="https://jitpack.io/#RhymezxCode/NetworkStateObserver" target="blank">
    <img src="https://jitpack.io/v/RhymezxCode/NetworkStateObserver.svg" alt="NetworkStateObserver Android Library on jitpack.io" />
</a>
<a href="https://github.com/RhymezxCode/NetworkStateObserver/blob/main/LICENSE" target="blank">
    <img src="https://img.shields.io/github/license/RhymezxCode/NetworkStateObserver" alt="NetworkStateObserver Android Library License" />
</a>
<a href="https://github.com/RhymezxCode/NetworkStateObserver/stargazers" target="blank">
    <img src="https://img.shields.io/github/stars/RhymezxCode/NetworkStateObserver" alt="NetworkStateObserver Android Library Stars"/>
</a>
<a href="https://github.com/RhymezxCode/NetworkStateObserver/fork" target="blank">
    <img src="https://img.shields.io/github/forks/RhymezxCode/NetworkStateObserver" alt="NetworkStateObserver Android Library Forks"/>
</a>
<a href="https://github.com/RhymezxCode/NetworkStateObserver/issues" target="blank">
    <img src="https://img.shields.io/github/issues/RhymezxCode/NetworkStateObserver" alt="NetworkStateObserver Android Library Issues"/>
</a>
</div>

<div align="center">
    <sub>Built with ❤︎ by
        <a href="https://github.com/RhymezxCode">Awodire Babajide Samuel</a>
    </sub>
</div>

<br />

# NetworkStateObserver Android Library
A library that helps you check the state of your network, if it is either available, lost, unavailable and also check the reach-ability of your network when your server is either down or your ISP is connected but no data subscription. 

## Adding NetworkStateObserver to your project

Include jitpack in your root `settings.gradle` file.

```gradle
pluginManagement {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

And add it's dependency to your app level `build.gradle` file:

```gradle
dependencies {
    implementation 'com.github.RhymezxCode:NetworkStateObserver:1.0.1'
}
```

Sync your project, and boom you have added NetworkStateObserver successfully.


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
