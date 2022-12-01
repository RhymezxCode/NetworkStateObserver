<div align="center">
<h1>NetworkStateObserver Android Library</h1>

<a href="https://android-arsenal.com/api?level=21" target="blank">
    <img src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" alt="NetworkStateObserver Android Library least API level" />
</a>
<a href="https://jitpack.io/#RhymezxCode/NetworkStateObserver" target="blank">
    <img src="https://jitpack.io/v/RhymezxCode/NetworkStateObserver.svg" alt="NetworkStateObserver Android Library on jitpack.io" />
</a>
<a href="https://github.com/RhymezxCode/NetworkStateObserver/blob/main/LICENSE" target="blank">
    <img src="https://img.shields.io/github/license/RhymezxCode/NetworkStateObserver" alt="NetworkStateObserver Android Library License." />
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
<a href="https://github.com/RhymezxCode/NetworkStateObserver/commits?author=RhymezxCode" target="blank">
    <img src="https://img.shields.io/github/last-commit/RhymezxCode/NetworkStateObserver" alt="NetworkStateObserver Android Library Issues"/>
</a>
</div>
<br />

## NetworkStateObserver Android Library
A library that helps you check the state of your network, if it is either available, lost, unavailable and also check the reach-ability of your network when your server is either down or your ISP is connected but no data subscription. 

Demo:

![](Demo.gif)

### 1. Adding NetworkStateObserver to your project

* Include jitpack in your root `settings.gradle` file.

```gradle
pluginManagement {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

* And add it's dependency to your app level `build.gradle` file:

```gradle
dependencies {
    implementation 'com.github.RhymezxCode:NetworkStateObserver:1.0.1'
}
```

#### Sync your project, and :scream: boom :fire: you have added NetworkStateObserver successfully. :exclamation:

### 2. Usage

* First initialize the builder class:

```
        val network = NetworkStateObserver.Builder()
            .activity(activity = this@NetworkStateObserverExample)
            .build()
```

* If you just want to check for connectivity, before performing a task or job():

```
        if(CheckConnectivity.isNetworkAvailable(requireContext())){
                         showToast(
                                this@NetworkStateObserverExample,
                                "Network restored"
                            )
        }
```

* Use the live-data method to determine your network state, and replace the code in the lifecycleScope.launchWhenStarted { ....your code here } to do what you want:

```
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
 ```

* You can check if your internet connection is stable only, if you don't have a server url: 

```
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
```
    
### 3. You can also inject NetworkStateObserver, and use it everywhere in your app with Hilt :syringe: :

* Create an object for the NetworkStateModule in your di package:

```
@Module
@InstallIn(ActivityComponent::class)
object NetworkStateModule {
    @Provides
    fun provideNetworkStateObserver(
        activity: Activity
    ): NetworkStateObserver {
        return NetworkStateObserver.Builder()
            .activity(activity = activity)
            .build()
    }
}
```

* Declare the variable in your class either a fragment or activity, it works in both:

```
@AndroidEntryPoint
class myFragment : Fragment(){
     @Inject
     lateinit var network: NetworkStateObserver

     private fun callNetworkConnection() {
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
    }

 override fun onResume() {
        super.onResume()
        callNetworkConnection()
    }
    
 }
 ```

* Add the method in onResume() of your fragment or activity to have a great experience:

```
    override fun onResume() {
        super.onResume()
        callNetworkConnection()
    }
```

:pushpin: Please, feel free to give me a star :star2:, I also love sparkles :sparkles: :relaxed:
<div align="center">
    <sub>Developed with :sparkling_heart: by
        <a href="https://github.com/RhymezxCode">Awodire Babajide Samuel</a>
    </sub>
</div>

