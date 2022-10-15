package com.fidilaundry.app.ui.base

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.esotericsoftware.minlog.Log
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.util.BaseContextWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import kotlin.coroutines.CoroutineContext


/**
 * DatabindingActivity is an abstract class for providing [DataBindingUtil].
 * provides implementations of only [ViewDataBinding] from an abstract information.
 * Do not modify this class. This is a first-level abstraction class.
 * If you want to add more specifications, make another class which extends [BaseActivity].
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope, LifecycleObserver {

    private var job: Job = Job()
    lateinit var paperPref: PaperPrefs
    private lateinit var currentTheme: String
    private var isLangDif: Boolean = false

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    protected inline fun <reified T : ViewDataBinding> binding(
            @LayoutRes resId: Int
    ): Lazy<T> = lazy { DataBindingUtil.setContentView<T>(this, resId) }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(BaseContextWrapper.wrap(newBase!!, Locale("en")))
        /*paperPref = PaperPrefs(newBase!!)
        if(paperPref.getLang().toString() == ""){
            //Log.info("cekLang, if "+paperPref.getLang().toString() + " " + newBase.resources.configuration.locale.toString())
            var lang = "in"
            paperPref.setLang(lang)
            //paperPref.setLangActive(lang)
            val locale = Locale(lang)
            super.attachBaseContext(BaseContextWrapper.wrap(newBase, locale))
        }
        else if(paperPref.getLang().toString() != newBase.resources.configuration.locale.toString()){
            //Log.info("cekLang, else if "+paperPref.getLang().toString() + " " + newBase.resources.configuration.locale.toString())
            isLangDif = true
            var lang = paperPref.getLang().toString()
            if (lang == "") {
                lang = "en"
                //paperPref.setLangActive("en")
            }
            paperPref.setLangActive(lang)
            val locale = Locale(lang)
            super.attachBaseContext(BaseContextWrapper.wrap(newBase, locale))
        }
        else{
            //Log.info("cekLang, else "+paperPref.getLang().toString() + " " + newBase.resources.configuration.locale.toString())
            var lang = paperPref.getLang().toString()
            if (lang == "") {
                lang = "en"
                //paperPref.setLangActive("en")
            }
            //paperPref.setLangActive(lang)
            val locale = Locale(lang)
            super.attachBaseContext(BaseContextWrapper.wrap(newBase, locale))
        }*/
    }
}
