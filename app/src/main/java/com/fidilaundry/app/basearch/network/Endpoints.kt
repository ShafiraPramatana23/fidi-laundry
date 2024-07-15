package com.fidilaundry.app.basearch.network

import androidx.room.FtsOptions.Order
import com.fidilaundry.app.model.request.*
import com.fidilaundry.app.model.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface Endpoints {
    @POST("auth/profile")
    suspend fun profile(@Header("Authorization") auth: String): ProfileResponse

    @POST("auth/change-profile")
    suspend fun changeProfile(
        @Header("Authorization") auth: String,
        @Body req: ChangeProfileRequest
    ): BaseObjResponse

    @POST("auth/change-password")
    suspend fun changePass(
        @Header("Authorization") auth: String,
        @Body req: ChangePassRequest
    ): BaseObjResponse

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
    ): RequestOrderResponse

    @POST("order/update")
    suspend fun updateOrder(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: UpdateOrderRequest
    ): BaseResponse

    @POST("order/update/status")
    suspend fun updateOrderStatus(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: UpdateOrderStatusRequest
    ): UpdateStatusResponse

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
        @Header("Authorization") auth: String,
        @Query("status") status: String
    ): OrderListResponse

    @GET("order/show/{id}")
    suspend fun getOrderDetail(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): OrderDetailResponse

    @GET("order/report?")
    suspend fun getReport(
        @Header("Authorization") auth: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("year") year: String,
        @Query("status") status: String
    ): OrderListResponse

    @POST("tracking/create")
    suspend fun addTracking(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: AddTrackingRequest
    ): BaseResponse

    @POST("tracking/show/{id}")
    suspend fun getTrackingList(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): TrackingListResponse

    @POST("ticket/create")
    suspend fun createComplaint(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: UserComplaintRequest
    ): BaseResponse

    @Multipart
    @POST("files/upload")
    suspend fun uploadFile(
        @Header("Authorization") auth: String,
        @Body req: AddTrackingRequest,
        @Part file: MultipartBody.Part
    ): BaseResponse

    @GET("ticket/report")
    suspend fun getComplaintList(
        @Header("Authorization") auth: String
    ): ComplaintListResponse

    @POST("ticket/feedback")
    suspend fun feedbackComplaint(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: ComplaintFeedbackRequest
    ): BaseResponse

    @GET("payment?")
    suspend fun getPaymentList(
        @Header("Authorization") auth: String,
        @Query("order_id") orderId: Int
    ): PaymentListResponse

    @POST("payment")
    suspend fun createPaymentMidtrans(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: CreatePaymentRequest
    ): CreatePaymentResponse

    @PUT("payment")
    suspend fun updatePaymentMidtrans(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: UpdatePaymentRequest
    ): BaseResponse

    /*@GET("order/list-all?")
    suspend fun getPaymentList(
        @Header("Authorization") auth: String,
        @Query("order_id") orderId: String,
        @Query("user_id") userId: String,
        @Query("payment_type") paymentType: String,
        @Query("status") status: String
    ): PaymentListResponse*/

    @POST("one/send-notification")
    suspend fun sendNotif(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body req: SendNotifRequest
    ): BaseObjResponse

}