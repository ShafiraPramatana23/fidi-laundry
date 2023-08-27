package com.fidilaundry.app.model.request

class UserComplaintRequest (
        var order_id: Int,
        var category: String,
        var description: String,
        var file: String
)