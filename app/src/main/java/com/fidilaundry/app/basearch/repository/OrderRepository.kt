package com.fidilaundry.app.basearch.repository

import androidx.room.FtsOptions.Order
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.OrderRequest
import com.fidilaundry.app.model.request.UpdateOrderRequest
import com.fidilaundry.app.model.response.*
import com.fidilaundry.app.util.Constant

interface OrderRepository {
    suspend fun getItemByService(id: Int): UseCaseResult<ItemListResponse>
    suspend fun requestOrder(req: OrderRequest): UseCaseResult<BaseObjResponse>
    suspend fun updateOrder(req: UpdateOrderRequest): UseCaseResult<BaseResponse>
    suspend fun getOrderList(custId: String, serviceId: String, step: String, status: String): UseCaseResult<OrderListResponse>
    suspend fun getOrderListCust(custId: String, serviceId: String, step: String, status: String): UseCaseResult<OrderListResponse>
    suspend fun getOrderDetail(id: String): UseCaseResult<OrderDetailResponse>
}

class OrderRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) :
    OrderRepository {

    override suspend fun getItemByService(id: Int): UseCaseResult<ItemListResponse> {
        return try {
            val result = api.getItemListByService(paperPrefs.getToken(), id)
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

    override suspend fun requestOrder(req: OrderRequest): UseCaseResult<BaseObjResponse> {
        return try {
            val contentType = "application/json"
            val result = api.requestOrder(paperPrefs.getToken(), contentType, req)
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

    override suspend fun updateOrder(req: UpdateOrderRequest): UseCaseResult<BaseResponse> {
        return try {
            val contentType = "application/json"
            val result = api.updateOrder(paperPrefs.getToken(), contentType, req)
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

    override suspend fun getOrderList(
        custId: String,
        serviceId: String,
        step: String,
        status: String
    ): UseCaseResult<OrderListResponse> {
        return try {
            val result = api.getOrderList(paperPrefs.getToken(), custId, serviceId, step, status)
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

    override suspend fun getOrderListCust(
        custId: String,
        serviceId: String,
        step: String,
        status: String
    ): UseCaseResult<OrderListResponse> {
        return try {
            val result = api.getOrderListCust(paperPrefs.getToken())
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

    override suspend fun getOrderDetail(id: String): UseCaseResult<OrderDetailResponse> {
        return try {
            val result = api.getOrderDetail(paperPrefs.getToken(), id)
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.status?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

}