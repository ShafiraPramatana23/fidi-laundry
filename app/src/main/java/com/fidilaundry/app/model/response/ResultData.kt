package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultData {
    @SerializedName("ID")
    @Expose
    var id: String? = null

    @SerializedName("Title")
    @Expose
    var title: String? = null
}