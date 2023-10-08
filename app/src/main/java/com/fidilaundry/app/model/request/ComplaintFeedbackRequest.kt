package com.fidilaundry.app.model.request

class ComplaintFeedbackRequest(
    var ticket_id: Int,
    var description: String,
    var file: String
)