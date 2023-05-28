package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("Results")
    @Expose
    var results: Results? = null

    @SerializedName("Status")
    @Expose
    var status: Status? = null

    class Results {
        @SerializedName("Authorize")
        @Expose
        var authorize: Boolean? = null

        @SerializedName("Message")
        @Expose
        var message: String? = null

        @SerializedName("Token")
        @Expose
        var token: String? = null
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