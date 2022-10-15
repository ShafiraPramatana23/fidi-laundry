package com.fidilaundry.app.util.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.fidilaundry.app.ext.onFalse
import com.fidilaundry.app.ext.onTrue

fun Any.toLDString(): LiveData<String> = toMLDString()
fun Any.toMLDString() = MutableLiveData(toString())


fun <T> T.toLiveData(): LiveData<T> = toMutableLiveData()
fun <T> T.toMutableLiveData() = MutableLiveData<T>(this)

fun <T> MutableLiveData<T>.setIfNotTheSame(newValue: T?) {
    if (value != newValue) value = newValue
}

class MutableLiveDataTextWithValidation(
        vararg validationErrorPairs: Pair<String.() -> Boolean, String>,
        defaultText: String = "",
        defaultErrorText: String = ""
) {

    val text = NonNullMutableLiveData(defaultText)
    val errorText = NonNullMediatorLiveData(defaultErrorText).apply {
        addSource(text) {
            // Break at the first violation
            validationErrorPairs.asSequence().any { (rule, error) ->
                val violation = !text.value.rule()
                violation.onTrue {
                    value = error
                }
            }.onFalse { value = defaultErrorText }
        }
    }
}

class NonNullMutableLiveData<T>(private val defaultValue: T) :
        MutableLiveData<T>() {
    override fun getValue(): T {
        return super.getValue() ?: defaultValue
    }
}

class NonNullMediatorLiveData<T>(private val defaultValue: T) :
        MediatorLiveData<T>() {
    override fun getValue(): T {
        return super.getValue() ?: defaultValue
    }
}
