package com.fidilaundry.app.model.request

class UpdatePaymentRequest (
        var payment_id: Int,
        var status: String,
        var payment: Double
)