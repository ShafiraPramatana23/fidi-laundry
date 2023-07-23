package com.fidilaundry.app.model.request

class UpdateItemRequest (
        var id: Int,
        var title: String,
        var category_id: String,
        var service_id: String,
        var price: Int
)