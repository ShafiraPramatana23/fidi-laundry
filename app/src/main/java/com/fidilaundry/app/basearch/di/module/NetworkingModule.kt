package com.fidilaundry.app.basearch.di.module

import android.annotation.SuppressLint
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.fidilaundry.app.MainApplicationContract
import com.fidilaundry.app.basearch.network.*
import com.fidilaundry.app.util.okhttphelper.*
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


val networkingModule = module {


    single<Endpoints> { get<Retrofit>(named(baseRetrofitName)).create(Endpoints::class.java) }

    single<Resources> { androidContext().resources }

    single<Gson> {
        GsonBuilder()
            .serializeNulls()
            .create()
    }

    single<Retrofit>(named(baseNoAuth)) {
        Retrofit.Builder()
                .baseUrl(MainApplicationContract.API_BASE_URL)
                .client(get<OkHttpClient>(named(noAuth)))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
    }

    single<Retrofit>(named(baseRetrofitName)) {
        Retrofit.Builder()
            .baseUrl(MainApplicationContract.API_BASE_URL)
            .client(get<OkHttpClient>(named(withAuth)))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<Retrofit>(named(baseGTMtName)) {
        Retrofit.Builder()
            .baseUrl(MainApplicationContract.API_GTM)
            .client(get<OkHttpClient>(named(withAuth)))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<Retrofit>(named(baseDistrict)) {
        Retrofit.Builder()
            .baseUrl(MainApplicationContract.API_DISTRICT)
            .client(get<OkHttpClient>(named(noAuth)))
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<Retrofit>(named(baseGeo)) {
        Retrofit.Builder()
                .baseUrl(MainApplicationContract.API_GEO)
                .client(get<OkHttpClient>(named(noAuth)))
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
    }

    single<OkHttpClient>(named(withAuth)) {

        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return emptyArray()
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val encryptionInterceptor = EncryptionInterceptor(EncryptionImpl())
        val decryptionInterceptor = DecryptionInterceptor(DecryptionImpl())

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        return@single OkHttpClient.Builder()
            .dispatcher(dispatcher)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(UserAgentInterceptor())
//            .addInterceptor(RefreshTokenInterceptor(paperPrefs = get(), context = androidContext()))
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier(HostnameVerifier { p0, p1 -> true })
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .build()
    }

    single<OkHttpClient>(named(noAuth)) {

        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return emptyArray()
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val encryptionInterceptor = EncryptionInterceptor(EncryptionImpl())
        val decryptionInterceptor = DecryptionInterceptor(DecryptionImpl())

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        return@single OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(UserAgentInterceptor())
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier(HostnameVerifier { p0, p1 -> true })
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .build()
    }

}
