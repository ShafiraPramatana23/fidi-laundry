package com.fidilaundry.app.basearch.network

import com.fidilaundry.app.model.response.ProfileResponse
import retrofit2.http.*

interface Endpoints {
    @POST("auth/profile")
    suspend fun profile(@Header("Authorization") auth: String): ProfileResponse
}