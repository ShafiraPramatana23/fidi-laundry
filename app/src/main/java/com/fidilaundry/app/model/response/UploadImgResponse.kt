package com.fidilaundry.app.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UploadImgResponse {
    @SerializedName("kind")
    @Expose
    var kind: String? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("selfLink")
    @Expose
    var selfLink: String? = null

    @SerializedName("mediaLink")
    @Expose
    var mediaLink: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("bucket")
    @Expose
    var bucket: String? = null

    @SerializedName("generation")
    @Expose
    var generation: String? = null

    @SerializedName("metageneration")
    @Expose
    var metageneration: String? = null

    @SerializedName("contentType")
    @Expose
    var contentType: String? = null

    @SerializedName("storageClass")
    @Expose
    var storageClass: String? = null

    @SerializedName("size")
    @Expose
    var size: String? = null

    @SerializedName("md5Hash")
    @Expose
    var md5Hash: String? = null

    @SerializedName("crc32c")
    @Expose
    var crc32c: String? = null

    @SerializedName("etag")
    @Expose
    var etag: String? = null

    @SerializedName("timeCreated")
    @Expose
    var timeCreated: String? = null

    @SerializedName("updated")
    @Expose
    var updated: String? = null

    @SerializedName("timeStorageClassUpdated")
    @Expose
    var timeStorageClassUpdated: String? = null
}

