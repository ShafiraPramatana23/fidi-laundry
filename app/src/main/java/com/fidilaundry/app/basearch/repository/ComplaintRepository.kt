package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.UpdateOrderStatusRequest
import com.fidilaundry.app.model.request.UserComplaintRequest
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.UpdateStatusResponse
import com.fidilaundry.app.util.Constant

interface ComplaintRepository {
    suspend fun addUserComplaint(req: UserComplaintRequest): UseCaseResult<BaseResponse>
    suspend fun updateOrderStatus(req: UpdateOrderStatusRequest): UseCaseResult<UpdateStatusResponse>
}

class ComplaintRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) :
    ComplaintRepository {

    override suspend fun addUserComplaint(req: UserComplaintRequest): UseCaseResult<BaseResponse> {
        return try {
            val contentType = "application/json"
            val result = api.createComplaint(paperPrefs.getToken(), contentType, req)
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

    override suspend fun updateOrderStatus(req: UpdateOrderStatusRequest): UseCaseResult<UpdateStatusResponse> {
        return try {
            val contentType = "application/json"
            val result = api.updateOrderStatus(paperPrefs.getToken(), contentType, req)
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