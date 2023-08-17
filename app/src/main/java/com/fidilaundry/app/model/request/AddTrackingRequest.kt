package com.fidilaundry.app.model.request

class AddTrackingRequest (
        var order_id: Int,
        var latitude: String,
        var longitude: String,
        var note: String
)