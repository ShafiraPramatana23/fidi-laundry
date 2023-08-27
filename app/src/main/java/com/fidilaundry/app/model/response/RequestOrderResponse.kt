package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RequestOrderResponse {
    @SerializedName("Results")
    @Expose
    var results: Results? = null

    @SerializedName("Status")
    @Expose
    var status: Status? = null

    class Results {
        @SerializedName("OrderCode")
        @Expose
        var orderCode: String? = null

        @SerializedName("ServiceID")
        @Expose
        var serviceID: Int? = null
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