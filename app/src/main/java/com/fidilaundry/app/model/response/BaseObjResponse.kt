package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseObjResponse {
    @SerializedName("Results")
    @Expose
    var resultsArr: Msg? = null

    @SerializedName("Status")
    @Expose
    var status: Status? = null

    class Msg {
        @SerializedName("Message")
        @Expose
        var message: String? = null
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