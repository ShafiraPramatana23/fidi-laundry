package com.fidilaundry.app.basearch.util

import com.bumptech.glide.load.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object Utils {
    fun handleException(exception: Throwable):String {
        println(exception)
        return  when (exception) {
            is SocketTimeoutException -> "Request time out. Try again"
            is UnknownHostException -> "Check your internet connection"
            is HttpException -> "Check your internet connection"
            is ConnectException -> "Check your internet connection"
            else -> "Need other process for this feature"
        }
    }
}