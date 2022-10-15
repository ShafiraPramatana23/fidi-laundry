package com.fidilaundry.app.util.okhttphelper

import android.os.Build
import com.fidilaundry.app.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UserAgentInterceptor : Interceptor {

    companion object {
        private const val USER_AGENT = "User-Agent"
    }

    private val userAgent: String =
        "PosGO Syariah/ " +
                "${BuildConfig.VERSION_NAME} " +
                "(${BuildConfig.APPLICATION_ID}; " +
                "build:${BuildConfig.VERSION_CODE} " +
                "Android SDK ${Build.VERSION.SDK_INT}) " +
                Build.MODEL


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithUserAgent = request.newBuilder()
            .header(USER_AGENT, userAgent)
            .build()

        return chain.proceed(requestWithUserAgent)
    }
}