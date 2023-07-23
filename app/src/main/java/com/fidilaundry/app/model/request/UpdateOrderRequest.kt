package com.fidilaundry.app.model.request

class UpdateOrderRequest (
        var code: String,
        var status: String,
        var latitude: String,
        var longitude: String,
        var stepping: String
)