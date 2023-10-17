package com.fidilaundry.app.model.request

class OrderRequest (
        var service_id: Int,
        var latitude: String,
        var longitude: String,
        var addressDescription: String,
        var transferMethod: String,
        var order_for: Int,
        var user_id: Int
)