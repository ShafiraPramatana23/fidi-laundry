package com.fidilaundry.app

import android.app.Application
import com.onesignal.OneSignal
import com.fidilaundry.app.basearch.di.modulesList
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import org.koin.core.logger.Level
import java.net.URISyntaxException

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()

        // start Koin context
        startKoin {
            androidLogger(Level.ERROR)
            modules(
                modulesList
            ).androidContext(this@MainApplication)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + "::Line:" + element.lineNumber + "::" + element.methodName + "()"
                }
            })
        }
    }

}