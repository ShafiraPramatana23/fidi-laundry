package com.fidilaundry.app.model.request

class AddComplaintRequest (
        var category: Int,
        var latitude: String,
        var longitude: String,
        var note: String
)