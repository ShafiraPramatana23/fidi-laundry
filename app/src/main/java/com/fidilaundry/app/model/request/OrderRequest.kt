package com.fidilaundry.app.model.request

class OrderRequest (
        var service_id: String,
        var latitude: String,
        var longitude: String,
        var addressDescription: String,
        var transferMethod: String
)