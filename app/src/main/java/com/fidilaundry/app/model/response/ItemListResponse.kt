package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ItemListResponse : Serializable {
    @SerializedName("Results")
    @Expose
    var results: List<Result>? = null

    @SerializedName("Status")
    @Expose
    var status: Status? = null

    class Result : Serializable {
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
    
    class Status : Serializable {
        @SerializedName("Code")
        @Expose
        var code: Int? = null

        @SerializedName("Message")
        @Expose
        var message: String? = null
    }
}