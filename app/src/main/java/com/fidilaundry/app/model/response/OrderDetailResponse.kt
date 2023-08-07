package com.fidilaundry.app.model.response

import com.fidilaundry.app.model.response.OrderListResponse.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class OrderDetailResponse {
    @SerializedName("Results")
    @Expose
    var results: Result? = null

    @SerializedName("Status")
    @Expose
    var status: Status? = null

    class Result {
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

        @SerializedName("Tracking")
        @Expose
        var tracking: List<Any>? = null

        @SerializedName("OrderItems")
        @Expose
        var orderItems: List<OrderItem>? = null
    }

    class Status {
        @SerializedName("Code")
        @Expose
        var code: Int? = null

        @SerializedName("Message")
        @Expose
        var message: String? = null
    }

    class OrderItem {
        @SerializedName("ID")
        @Expose
        var id: Int? = null

        @SerializedName("CreatedAt")
        @Expose
        var createdAt: String? = null

        @SerializedName("OrderID")
        @Expose
        var orderID: Int? = null

        @SerializedName("ItemID")
        @Expose
        var itemID: Int? = null

        @SerializedName("Amount")
        @Expose
        var amount: Int? = null

        @SerializedName("Qty")
        @Expose
        var qty: Int? = null

        @SerializedName("Item")
        @Expose
        var item: Item? = null
    }

    class Item {
        @SerializedName("ID")
        @Expose
        var id: Int? = null

        @SerializedName("CreatedAt")
        @Expose
        var createdAt: String? = null

        @SerializedName("Code")
        @Expose
        var code: String? = null

        @SerializedName("Title")
        @Expose
        var title: String? = null

        @SerializedName("CategoryID")
        @Expose
        var categoryID: String? = null

        @SerializedName("CategoryTitle")
        @Expose
        var categoryTitle: String? = null

        @SerializedName("ServiceID")
        @Expose
        var serviceID: String? = null

        @SerializedName("ServiceTitle")
        @Expose
        var serviceTitle: String? = null

        @SerializedName("Price")
        @Expose
        var price: Int? = null
    }
}