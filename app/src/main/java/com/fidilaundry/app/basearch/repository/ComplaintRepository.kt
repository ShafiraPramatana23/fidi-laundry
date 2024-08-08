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
    suspend fun addUserComplaint(req: RequestBody): UseCaseResult<BaseResponse>
//    suspend fun addUserComplaint(req: UserComplaintRequest): UseCaseResult<BaseResponse>
    suspend fun updateOrderStatus(req: UpdateOrderStatusRequest): UseCaseResult<UpdateStatusResponse>
    suspend fun feedbackComplaint(req: RequestBody): UseCaseResult<BaseResponse>
    suspend fun getComplaint(): UseCaseResult<ComplaintListResponse>
    suspend fun uploadImg(type: String, name: String, req: RequestBody): UseCaseResult<UploadImgResponse>
}

class ComplaintRepositoryImpl(private val api: Endpoints, private val apiUpload: EndpointsUpload, private val paperPrefs: PaperPrefs) :
    ComplaintRepository {

    override suspend fun addUserComplaint(req: RequestBody): UseCaseResult<BaseResponse> {
        return try {
            val contentType = "application/json"
            val result = api.createComplaint(paperPrefs.getToken(), req)
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

    override suspend fun feedbackComplaint(req: RequestBody): UseCaseResult<BaseResponse> {
        return try {
            val contentType = "application/json"
            val result = api.feedbackComplaint(paperPrefs.getToken(), req)
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
            val token = "Bearer ya29.a0AXooCgvNOWgm3c-5zH5rXv0WyA9RjTKMkcp0h-m77rzfYPLAOPQqORZuUfdG07NoWPQCSH2tS0DNjqOmncorQm9SbUEROUuYJiBDjHc4DfMwSW8JoNUPg4Qn0s1_kCtxXjEJ8anVIrHpIcpNHTlrxqdbKQjpUOXapHfeaCgYKAe0SARISFQHGX2MiEYFk8AjMzJllBEvS5Ddt5w0171"
            val result = apiUpload.uploadImg(token, contentType, "media", name, req)
            UseCaseResult.Success(result)
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }
}