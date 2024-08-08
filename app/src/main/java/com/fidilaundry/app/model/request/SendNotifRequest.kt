package com.fidilaundry.app.model.request

class SendNotifRequest (
        var title: String,
        var to_user_id: Int,
        var message: String,
        var order_id: Int
)