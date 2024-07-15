package com.fidilaundry.app.basearch.network

import com.fidilaundry.app.model.request.*
import com.fidilaundry.app.model.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface EndpointsUpload {

    @POST("fidi_laundry/o?")
    suspend fun uploadImg(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Query("uploadType") uploadType: String,
        @Query("name") name: String,
        @Body body: RequestBody
    ): UploadImgResponse
}