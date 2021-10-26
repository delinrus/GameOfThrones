package ru.skillbranch.gameofthrones.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.repositories.RootRepository

class RootViewModel(application: Application) : AndroidViewModel(application) {

    private val _isDataSynchronized = MutableLiveData<SynchronizationResult>()
    val isDataSynchronized: LiveData<SynchronizationResult>
        get() = _isDataSynchronized

    private val scope = CoroutineScope(Dispatchers.IO)

    fun synchronizeData() {
        scope.launch {
            val delayJob = launch {
                delay(5000)
            }
            if (!RootRepository.isUpToDate()) {
                if (isNetworkAvailable(getApplication())) {
                    RootRepository.sync()
                } else {
                    _isDataSynchronized.postValue(SynchronizationResult.NO_CONNECTION)
                    return@launch
                }
            }
            delayJob.join()
            _isDataSynchronized.postValue(SynchronizationResult.FINISHED)
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

    enum class SynchronizationResult {
        FINISHED, NO_CONNECTION
    }
}