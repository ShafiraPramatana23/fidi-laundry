package com.fidilaundry.app.util.okhttphelper

import android.text.TextUtils
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import timber.log.Timber
import java.io.IOException


class DecryptionInterceptor(mDecryptionStrategy: CryptoStrategy?) : Interceptor {
    private val mDecryptionStrategy: CryptoStrategy?
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.i("===============DECRYPTING RESPONSE===============")
        val response: Response = chain.proceed(chain.request())
        if (response.isSuccessful) {
            val newResponse: Response.Builder = response.newBuilder()
            var contentType: String = response.header("Content-Type")!!
            if (TextUtils.isEmpty(contentType)) contentType = "application/json"
            val responseString: String = response.body!!.string()
            var decryptedString: String? = null
            if (mDecryptionStrategy != null) {
                try {
                    decryptedString = mDecryptionStrategy.decrypt(responseString)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Timber.i("Response string => %s", responseString)
                Timber.i("Decrypted BODY=> %s", decryptedString)
            } else {
                throw IllegalArgumentException("No decryption strategy!")
            }
            newResponse.body(ResponseBody.create(contentType.toMediaTypeOrNull(), decryptedString!!))
            return newResponse.build()
        }
        return response
    }

    //injects the type of decryption to be used
    init {
        this.mDecryptionStrategy = mDecryptionStrategy
    }
}