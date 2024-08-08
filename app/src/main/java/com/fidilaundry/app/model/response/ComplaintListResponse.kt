package com.fidilaundry.app.model.response

import com.fidilaundry.app.model.response.OrderListResponse.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ComplaintListResponse: Serializable {
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

        @SerializedName("OrderID")
        @Expose
        var orderID: Int? = null

        @SerializedName("Category")
        @Expose
        var category: String? = null

        @SerializedName("Description")
        @Expose
        var description: String? = null

        @SerializedName("File")
        @Expose
        var file: String? = null

        @SerializedName("PhotoPath")
        @Expose
        var photoPath: String? = null

        @SerializedName("Feedbacks")
        @Expose
        var feedbacks: List<Feedback>? = null

        @SerializedName("User")
        @Expose
        var user: User? = null

        @SerializedName("Order")
        @Expose
        var order: Order? = null
    }

    class Feedback: Serializable {
        @SerializedName("ID")
        @Expose
        var id: Int? = null

        @SerializedName("CreatedAt")
        @Expose
        var createdAt: String? = null

        @SerializedName("TicketID")
        @Expose
        var ticketID: Int? = null

        @SerializedName("OrderID")
        @Expose
        var orderID: Int? = null

        @SerializedName("Description")
        @Expose
        var description: String? = null

        @SerializedName("File")
        @Expose
        var file: String? = null

        @SerializedName("PhotoPath")
        @Expose
        var photoPath: String? = null
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

        @SerializedName("User")
        @Expose
        var user: User? = null

        @SerializedName("OrderFor")
        @Expose
        var orderFor: OrderFor? = null

        @SerializedName("Tracking")
        @Expose
        var tracking: Any? = null

        @SerializedName("OrderItems")
        @Expose
        var orderItems: Any? = null
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