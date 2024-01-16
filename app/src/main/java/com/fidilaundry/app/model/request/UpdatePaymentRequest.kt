package com.fidilaundry.app.model.request

class UpdatePaymentRequest (
        var payment_id: String, //int
        var status: String,
        var payment: Double
)