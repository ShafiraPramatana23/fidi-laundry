package com.fidilaundry.app.model.request

class UpdatePaymentRequest (
        var payment_id: Int, //int
        var status: String,
        var payment: Double
)