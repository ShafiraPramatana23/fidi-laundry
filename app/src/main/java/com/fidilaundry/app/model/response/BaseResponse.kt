package com.fidilaundry.app.model.response

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("status")
    var status = 0
    @SerializedName("iserror")
    var iserror = false
    @SerializedName("message")
    var message: String? = null
}