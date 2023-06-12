package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ServiceListResponse {
    @SerializedName("Results")
    @Expose
    var results: List<ResultData>? = null

    @SerializedName("Status")
    @Expose
    var status: Status? = null

    class Result {
        @SerializedName("ID")
        @Expose
        var id: String? = null

        @SerializedName("Title")
        @Expose
        var title: String? = null
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