package uz.ismoil.notes.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T> MediatorLiveData<*>.addSourceDisposable(liveData: LiveData<T>, onChange: (T) -> Unit): MediatorLiveData<*> {
    addSource(liveData) {
        onChange(it)
        removeSource(liveData)
    }
    return this
}

fun <T> MediatorLiveData<*>.addSourceUntil(count: Int, liveData: LiveData<T>, onChange: (T) -> Unit): MediatorLiveData<*> {
    var call = 0
    addSource(liveData) {
        onChange(it)
        call++
        if (call == count) {
            removeSource(liveData)
        }
    }
    return this
}

fun <T> MediatorLiveData<*>.addSourceDisposableNoAction(liveData: LiveData<T>): MediatorLiveData<*> {
    addSource(liveData) {
        removeSource(liveData)
    }
    return this
}
