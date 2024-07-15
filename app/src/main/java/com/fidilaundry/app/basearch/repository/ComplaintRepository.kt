package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.network.EndpointsUpload
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.ComplaintFeedbackRequest
import com.fidilaundry.app.model.request.UpdateOrderStatusRequest
import com.fidilaundry.app.model.request.UserComplaintRequest
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.ComplaintListResponse
import com.fidilaundry.app.model.response.UpdateStatusResponse
import com.fidilaundry.app.model.response.UploadImgResponse
import com.fidilaundry.app.util.Constant
import okhttp3.RequestBody

interface ComplaintRepository {
    suspend fun addUserComplaint(req: UserComplaintRequest): UseCaseResult<BaseResponse>
    suspend fun updateOrderStatus(req: UpdateOrderStatusRequest): UseCaseResult<UpdateStatusResponse>
    suspend fun feedbackComplaint(req: ComplaintFeedbackRequest): UseCaseResult<BaseResponse>
    suspend fun getComplaint(): UseCaseResult<ComplaintListResponse>
    suspend fun uploadImg(type: String, name: String, req: RequestBody): UseCaseResult<UploadImgResponse>
}

class ComplaintRepositoryImpl(private val api: Endpoints, private val apiUpload: EndpointsUpload, private val paperPrefs: PaperPrefs) :
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

    override suspend fun feedbackComplaint(req: ComplaintFeedbackRequest): UseCaseResult<BaseResponse> {
        return try {
            val contentType = "application/json"
            val result = api.feedbackComplaint(paperPrefs.getToken(), contentType, req)
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

    override suspend fun getComplaint(): UseCaseResult<ComplaintListResponse> {
        return try {
            val contentType = "application/json"
            val result = api.getComplaintList(paperPrefs.getToken())
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

    override suspend fun uploadImg(type: String, name: String, req: RequestBody): UseCaseResult<UploadImgResponse> {
        return try {
            val contentType = "image/png"
            val token = "Bearer ya29.a0AXooCgtZegA02clPcTYuicL0Ph0OQC-7q6-Hhrz_dXvApdkzXnGbX39hXQWyy7fy8KGgzug8Qt2S4BLgOx7V0Uu473e6nXG6g70E4Tsp3MxgqTYGfn-a4Cn6aYYPyqEm2C5hmDaCccT1iSi1Ze0yBEQAlWfF68ig6639aCgYKAVgSARISFQHGX2MirkH2B5jcPREiKlAZM_iZMA0171"
            val result = apiUpload.uploadImg(token, contentType, "media", name, req)
            UseCaseResult.Success(result)
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }
}