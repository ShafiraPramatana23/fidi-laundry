package com.fidilaundry.app.basearch.repository

import androidx.room.FtsOptions.Order
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.AddTrackingRequest
import com.fidilaundry.app.model.request.OrderRequest
import com.fidilaundry.app.model.request.UpdateOrderRequest
import com.fidilaundry.app.model.request.UpdateOrderStatusRequest
import com.fidilaundry.app.model.response.*
import com.fidilaundry.app.util.Constant

interface TrackingRepository {
    suspend fun addTracking(req: AddTrackingRequest): UseCaseResult<BaseResponse>
    suspend fun getTracking(id: Int): UseCaseResult<TrackingListResponse>
}

class TrackingRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) :
    TrackingRepository {

    override suspend fun addTracking(req: AddTrackingRequest): UseCaseResult<BaseResponse> {
        return try {
            val contentType = "application/json"
            val result = api.addTracking(paperPrefs.getToken(), contentType, req)
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

    override suspend fun getTracking(id: Int): UseCaseResult<TrackingListResponse> {
        return try {
            val result = api.getTrackingList(paperPrefs.getToken(), id)
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