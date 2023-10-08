package com.fidilaundry.app

import android.app.Application
import com.fidilaundry.app.basearch.di.modulesList
import com.fidilaundry.app.util.OneSignalNotificationOpenHandler
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import com.onesignal.OneSignal.OSNotificationOpenedHandler
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(BuildConfig.ONESIGNAL_APP_ID)
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.setNotificationOpenedHandler(OneSignalNotificationOpenHandler(applicationContext))
//        OneSignal.setNotificationOpenedHandler(OSNotificationOpenedHandler { result: OSNotificationOpenedResult ->
//            OneSignal.onesignalLog(
//                OneSignal.LOG_LEVEL.VERBOSE,
//                "OSNotificationOpenedResult result: $result"
//            )
//        })
//        OneSignal.pauseInAppMessages(true)
//        OneSignal.setLocationShared(false)

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