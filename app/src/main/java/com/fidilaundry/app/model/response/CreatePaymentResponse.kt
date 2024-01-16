package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreatePaymentResponse {
    @SerializedName("Results")
    @Expose
    var results: Result? = null

    @SerializedName("Status")
    @Expose
    var status: Status? = null

    class Result {
        @SerializedName("snap_token")
        @Expose
        var snapToken: String? = null
    }

    class Status {
        @SerializedName("Code")
        @Expose
        var code: Int? = null

        @SerializedName("Message")
        @Expose
        var message: String? = null
    }
}