package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UpdateStatusResponse {
    @SerializedName("Results")
    @Expose
    var results: Result? = null

    @SerializedName("Status")
    @Expose
    var status: Status? = null

    class Result {
        @SerializedName("OrderCode")
        @Expose
        var orderCode: String? = null

        @SerializedName("ServiceID")
        @Expose
        var serviceID: Int? = null

        @SerializedName("Stepping")
        @Expose
        var stepping: String? = null
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