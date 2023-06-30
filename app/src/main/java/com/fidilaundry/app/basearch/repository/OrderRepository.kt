package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.ItemListResponse
import com.fidilaundry.app.util.Constant

interface OrderRepository {
    suspend fun getItemByService(id: String): UseCaseResult<ItemListResponse>
}

class OrderRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) :
    OrderRepository {

    override suspend fun getItemByService(id: String): UseCaseResult<ItemListResponse> {
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
}