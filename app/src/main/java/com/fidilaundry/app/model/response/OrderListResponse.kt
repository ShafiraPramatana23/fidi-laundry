package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class OrderListResponse: Serializable {
    @SerializedName("Results")
    @Expose
    var results: List<Result>? = null

    @SerializedName("Status")
    @Expose
    var status: Status? = null

    class Result: Serializable {
        @SerializedName("ID")
        @Expose
        var id: Int? = null

        @SerializedName("CreatedAt")
        @Expose
        var createdAt: String? = null

        @SerializedName("UserID")
        @Expose
        var userID: Int? = null

        @SerializedName("ServiceID")
        @Expose
        var serviceID: Int? = null

        @SerializedName("Code")
        @Expose
        var code: String? = null

        @SerializedName("Latitude")
        @Expose
        var latitude: String? = null

        @SerializedName("Longitude")
        @Expose
        var longitude: String? = null

        @SerializedName("AddressDescription")
        @Expose
        var addressDescription: String? = null

        @SerializedName("TransferMethod")
        @Expose
        var transferMethod: String? = null

        @SerializedName("Status")
        @Expose
        var status: String? = null

        @SerializedName("Stepping")
        @Expose
        var stepping: String? = null

        @SerializedName("User")
        @Expose
        var user: User? = null
    }

    class Status: Serializable  {
        @SerializedName("Code")
        @Expose
        var code: Int? = null

        @SerializedName("Message")
        @Expose
        var message: String? = null
    }

    class User: Serializable {
        @SerializedName("ID")
        @Expose
        var id: Int? = null

        @SerializedName("CreatedAt")
        @Expose
        var createdAt: String? = null

        @SerializedName("Name")
        @Expose
        var name: String? = null

        @SerializedName("Uniq")
        @Expose
        var uniq: String? = null

        @SerializedName("Email")
        @Expose
        var email: String? = null

        @SerializedName("Phone")
        @Expose
        var phone: String? = null

        @SerializedName("Role")
        @Expose
        var role: String? = null
    }
}