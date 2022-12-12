package com.fidilaundry.app.basearch.network

import com.fidilaundry.app.model.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Endpoints {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun doLogin(
        @Field("username") username: String,
        @Field("phone_number") phone_number: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): BaseResponse

    @FormUrlEncoded
    @POST("user/login")
    suspend fun register(
        @Field("fullname") fullname: String,
        @Field("email") email: String,
        @Field("phone_number") phone_number: String,
        @Field("password") password: String
    ): BaseResponse
}