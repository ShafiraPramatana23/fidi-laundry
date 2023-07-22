package com.fidilaundry.app.model.request

class UpdateItemRequest (
        var id: String,
        var title: String,
        var category_id: String,
        var service_id: String,
        var price: Double
)