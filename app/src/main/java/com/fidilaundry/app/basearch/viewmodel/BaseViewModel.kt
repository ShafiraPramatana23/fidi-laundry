package com.fidilaundry.app.basearch.viewmodel

import androidx.lifecycle.*
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope, LifecycleObserver {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    final override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    protected val scope = CoroutineScope(coroutineContext)

    val alertMessageLiveData = MutableLiveData<Throwable?>()

    val showProgressLiveData = SingleLiveEvent<Boolean>()
    val showError = SingleLiveEvent<String>()
    val showSessionTimeOut = SingleLiveEvent<String>()
    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }

    class DoubleTriggerLiveData<S, T>(s: LiveData<S>, t: LiveData<T>) :
            MediatorLiveData<Pair<S?, T?>>() {
        init {
            addSource(s) { value = it to t.value }
            addSource(t) { value = s.value to it }
        }
    }
}