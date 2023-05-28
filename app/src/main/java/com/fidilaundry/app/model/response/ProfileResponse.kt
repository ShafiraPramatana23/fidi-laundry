package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileResponse {
    @SerializedName("Results")
    @Expose
    var results: Results? = null

    @SerializedName("Status")
    @Expose
    var status: Status? = null

    class Results {
        @SerializedName("ID")
        @Expose
        var id: Int? = null

        @SerializedName("Name")
        @Expose
        var name: String? = null

        @SerializedName("Uniq")
        @Expose
        var uniq: String? = null

        @SerializedName("Email")
        @Expose
        var email: String? = null

        @SerializedName("Role")
        @Expose
        var role: String? = null

        @SerializedName("Phone")
        @Expose
        var phone: String? = null
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