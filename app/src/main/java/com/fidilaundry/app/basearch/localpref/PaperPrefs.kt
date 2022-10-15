package com.fidilaundry.app.basearch.localpref

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.paperdb.Paper
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PaperPrefs : CoroutineScope, LifecycleObserver {

    // Coroutine background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    constructor(application: Application) {
        Paper.init(application)
    }

    constructor(context: Context) {
        Paper.init(context)
    }

    fun deleteAllData() {
        val allKeys = Paper.book().allKeys
        allKeys.forEach {
            println("The element is $it")
            if (it != "isWalkthrough" && it != "lang" && it != "langActive" && it != "isFirstTime") {
                Paper.book().delete(it)
            }
        }
    }

    private fun getStringFromPaperPrefAsync(key: String): String {
        return Paper.book().read(key, "")
    }

    private fun getBooleanFromPaperPrefAsync(key: String, default: Boolean): Boolean {
        return Paper.book().read(key, default)
    }

    private fun getIntFromPaperPrefAsync(key: String, default: Int): Int {
        return Paper.book().read(key, default)
    }

    private fun getLongFromPaperPrefAsync(key: String, default: Long): Long {
        return Paper.book().read(key, default)
    }

    private fun saveStringToPaperPref(key: String, value: String) {
        launch {
            withContext(Dispatchers.IO) {
                Paper.book().write(key, value)
            }
        }
    }

    private fun saveBooleanToPaperPref(key: String, value: Boolean) {
        launch {
            withContext(Dispatchers.IO) {
                Paper.book().write(key, value)
            }
        }
    }

    private fun saveIntToPaperPref(key: String, value: Int) {
        launch {
            withContext(Dispatchers.IO) {
                Paper.book().write(key, value)
            }
        }
    }

    private fun saveLongToPaperPref(key: String, value: Long) {
        launch {
            withContext(Dispatchers.IO) {
                Paper.book().write(key, value)
            }
        }
    }



    fun setToken(token: String) {
        saveStringToPaperPref("TOKEN", token)
    }

    fun getToken(): String {
        return getStringFromPaperPrefAsync("TOKEN")
    }

    fun setWalkthrough() {
        saveStringToPaperPref("isWalkthrough", "true")
    }

    fun getWalkthrough(): String {
        return getStringFromPaperPrefAsync("isWalkthrough")
    }

    fun setFirstTime() {
        saveStringToPaperPref("isFirstTime", "false")
    }

    fun getFirstTime(): String {
        return getStringFromPaperPrefAsync("isFirstTime")
    }

    /*fun setDataLogin(dataLogin: LoginDataInside) {
        val gson = Gson()
        val json = gson.toJson(dataLogin)
        saveStringToPaperPref("dataLogin", json)
    }

    fun setDataProfile(dataProfile: ProfileData) {
        val gson = Gson()
        val json = gson.toJson(dataProfile)
        saveStringToPaperPref("dataProfile", json)
    }*/

}