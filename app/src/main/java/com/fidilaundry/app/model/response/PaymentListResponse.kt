package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PaymentListResponse: Serializable {
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

        @SerializedName("Order")
        @Expose
        var order: Order? = null

        @SerializedName("UserID")
        @Expose
        var userID: Int? = null

        @SerializedName("OrderID")
        @Expose
        var orderID: Int? = null

        @SerializedName("Payment")
        @Expose
        var payment: String? = null

        @SerializedName("PaymentType")
        @Expose
        var paymentType: Int? = null

        @SerializedName("Status")
        @Expose
        var status: String? = null

        @SerializedName("PaymentToken")
        @Expose
        var paymentToken: String? = null
    }

    class Order: Serializable {
        @SerializedName("ID")
        @Expose
        var id: Int? = null

        @SerializedName("CreatedAt")
        @Expose
        var createdAt: String? = null

        @SerializedName("UserID")
        @Expose
        var userID: Int? = null

        @SerializedName("OrderForID")
        @Expose
        var orderForID: Int? = null

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

        @SerializedName("Fee")
        @Expose
        var fee: Int? = null

        @SerializedName("Total")
        @Expose
        var total: Int? = null

        @SerializedName("TransferMethod")
        @Expose
        var transferMethod: String? = null

        @SerializedName("Status")
        @Expose
        var status: String? = null

        @SerializedName("Stepping")
        @Expose
        var stepping: String? = null

        @SerializedName("OrderFor")
        @Expose
        var orderFor: PaymentListResponse.OrderFor? = null
    }

    class OrderFor: Serializable {
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

        @SerializedName("DeviceID")
        @Expose
        var deviceID: String? = null
    }

    class Status: Serializable {
        @SerializedName("Code")
        @Expose
        var code: Int? = null

        @SerializedName("Message")
        @Expose
        var message: String? = null
    }
}