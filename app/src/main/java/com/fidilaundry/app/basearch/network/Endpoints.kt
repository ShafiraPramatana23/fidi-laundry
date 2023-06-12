package com.fidilaundry.app.basearch.network

import com.fidilaundry.app.model.request.AddCustomerRequest
import com.fidilaundry.app.model.request.AddItemRequest
import com.fidilaundry.app.model.response.*
import retrofit2.http.*

interface Endpoints {
    @POST("auth/profile")
    suspend fun profile(@Header("Authorization") auth: String): ProfileResponse

    @POST("customer/add")
    suspend fun addCustomer(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: AddCustomerRequest
    ): BaseResponse

//    @POST("master/category/list-all")
//    suspend fun getCustomerList(@Header("Authorization") auth: String): ProfileResponse

    @GET("master/service/list-all")
    suspend fun getServiceList(@Header("Authorization") auth: String): ServiceListResponse

    @GET("master/category/list-all")
    suspend fun getCategoryList(@Header("Authorization") auth: String): CategoryListResponse

    @GET("master/item/list-all")
    suspend fun getItemList(@Header("Authorization") auth: String): ItemListResponse

    @POST("master/item/create")
    suspend fun addItem(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: AddItemRequest
    ): BaseObjResponse
}