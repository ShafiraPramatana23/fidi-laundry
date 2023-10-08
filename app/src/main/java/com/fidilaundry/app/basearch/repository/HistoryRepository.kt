package com.fidilaundry.app.basearch.repository

import androidx.room.FtsOptions.Order
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.OrderRequest
import com.fidilaundry.app.model.request.UpdateOrderRequest
import com.fidilaundry.app.model.response.*
import com.fidilaundry.app.util.Constant

interface HistoryRepository {
    suspend fun getOrderList(custId: String, serviceId: String, step: String, status: String): UseCaseResult<OrderListResponse>
    suspend fun getOrderListCust(custId: String, serviceId: String, step: String, status: String): UseCaseResult<OrderListResponse>
    suspend fun getOrderDetail(id: String): UseCaseResult<OrderDetailResponse>
    suspend fun getReport(startDate: String, endDate: String, year: String): UseCaseResult<OrderListResponse>
}

class HistoryRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) :
    HistoryRepository {

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

    override suspend fun getReport(
        startDate: String,
        endDate: String,
        year: String
    ): UseCaseResult<OrderListResponse> {
        return try {
            val result = api.getReport(paperPrefs.getToken(), startDate, endDate, year)
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