package ru.skillbranch.gameofthrones.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.repositories.RootRepository

class RootViewModel : ViewModel() {

    private val _isDataSynchronized = MutableLiveData<Boolean>()
    val isDataSynchronized: LiveData<Boolean>
        get() = _isDataSynchronized

    private val scope = CoroutineScope(Dispatchers.IO)

    fun synchronizeData() {
        scope.launch {
            if (!RootRepository.isUpToDate()) {
                RootRepository.sync()
            }
            _isDataSynchronized.postValue(true)
        }
    }
}