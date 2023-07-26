package com.fidilaundry.app.basearch.network

import com.fidilaundry.app.model.request.*
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

    @POST("customer/list")
    suspend fun getCustomerList(@Header("Authorization") auth: String): CustomerListResponse

    @GET("master/service/list-all")
    suspend fun getServiceList(@Header("Authorization") auth: String): ServiceListResponse

    @GET("master/category/list-all")
    suspend fun getCategoryList(@Header("Authorization") auth: String): CategoryListResponse

    @GET("master/item/list-all")
    suspend fun getItemList(@Header("Authorization") auth: String): ItemListResponse

    @GET("master/item/search-by-service-id/{id}")
    suspend fun getItemListByService(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): ItemListResponse

    @POST("master/item/create")
    suspend fun addItem(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: AddItemRequest
    ): BaseObjResponse

    @POST("master/item/update")
    suspend fun updateItem(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: UpdateItemRequest
    ): BaseObjResponse

    @POST("master/item/delete")
    suspend fun deleteItem(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: DeleteItemRequest
    ): BaseObjResponse

    @POST("order/create")
    suspend fun requestOrder(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: OrderRequest
    ): BaseObjResponse

    @POST("order/update")
    suspend fun updateOrder(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: UpdateOrderRequest
    ): BaseResponse

    @GET("order/list-all?")
    suspend fun getOrderList(
        @Header("Authorization") auth: String,
        @Query("cust_id") custItem: String,
        @Query("service_id") serviceId: String,
        @Query("step") step: String,
        @Query("status") status: String
    ): OrderListResponse

    @GET("order/auth-user")
    suspend fun getOrderListCust(
        @Header("Authorization") auth: String
//        @Query("cust_id") custItem: String,
//        @Query("service_id") serviceId: String,
//        @Query("step") step: String,
//        @Query("status") status: String
    ): OrderListResponse

    @GET("order/show/{id}")
    suspend fun getOrderDetail(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): OrderDetailResponse
}