package com.fidilaundry.app.model.request

import com.google.gson.annotations.SerializedName

class UpdateOrderRequest (
        var code: String,
        var status: String,
        var latitude: String,
        var longitude: String,
        var stepping: String,
        var item_service: List<ItemService>,
        var fee: Int,
        var total: Int
)

class ItemService (
        var item_id: Int,
        var amount: Int,
        var qty: Int
)


//class UpdateOrderRequest (
//        @SerializedName("code") var code: String? = null,
//        @SerializedName("status") var status: String? = null,
//        @SerializedName("latitude") var latitude: String? = null,
//        @SerializedName("longitude") var longitude: String? = null,
//        @SerializedName("stepping") var stepping: String? = null,
//        @SerializedName("item_service") var itemService: List<ItemService>? = arrayListOf(),
//        @SerializedName("fee") var fee: Int? = null,
//        @SerializedName("total") var total: Int? = null
//)
