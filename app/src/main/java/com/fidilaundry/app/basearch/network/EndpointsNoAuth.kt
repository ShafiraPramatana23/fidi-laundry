package com.fidilaundry.app.basearch.network

import com.fidilaundry.app.model.request.LoginRequest
import com.fidilaundry.app.model.request.RegisterRequest
import com.fidilaundry.app.model.response.LoginResponse
import retrofit2.http.*

interface EndpointsNoAuth {
    @POST("auth/login")
    suspend fun doLogin(
        @Header("Content-Type") contentType: String,
        @Body req: LoginRequest
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Header("Content-Type") contentType: String,
        @Body req: RegisterRequest
    ): LoginResponse
}