package com.fidilaundry.app.util.okhttphelper

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import timber.log.Timber
import java.io.IOException


class EncryptionInterceptor(mEncryptionStrategy: CryptoStrategy?) : Interceptor {
    private val mEncryptionStrategy: CryptoStrategy?
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.i("===============ENCRYPTING REQUEST===============")
        var request: Request = chain.request()
        val rawBody: RequestBody = request.body!!
        var encryptedBody = ""
        val mediaType: MediaType = "text/plain; charset=utf-8".toMediaTypeOrNull()!!
        if (mEncryptionStrategy != null) {
            try {
                val rawBodyString: String = CryptoUtil.requestBodyToString(rawBody)
                encryptedBody = mEncryptionStrategy.encrypt(rawBodyString)
                Timber.i("Raw body=> %s", rawBodyString)
                Timber.i("Encrypted BODY=> %s", encryptedBody)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            throw IllegalArgumentException("No encryption strategy!")
        }
        val body: RequestBody = RequestBody.create(mediaType, encryptedBody)

        //build new request
        request = request.newBuilder()
            .header("Content-Type", body.contentType().toString())
            .header("Content-Length", body.contentLength().toString())
            .method(request.method, body).build()
        return chain.proceed(request)
    }

    companion object {
        private val TAG = EncryptionInterceptor::class.java.simpleName
    }

    //injects the type of encryption to be used
    init {
        this.mEncryptionStrategy = mEncryptionStrategy
    }
}