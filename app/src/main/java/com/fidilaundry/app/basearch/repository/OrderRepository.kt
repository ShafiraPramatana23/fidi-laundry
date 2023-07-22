package com.fidilaundry.app.basearch.repository

import androidx.room.FtsOptions.Order
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.OrderRequest
import com.fidilaundry.app.model.response.BaseObjResponse
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.ItemListResponse
import com.fidilaundry.app.util.Constant

interface OrderRepository {
    suspend fun getItemByService(id: Int): UseCaseResult<ItemListResponse>
    suspend fun requestOrder(req: OrderRequest): UseCaseResult<BaseObjResponse>
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
}