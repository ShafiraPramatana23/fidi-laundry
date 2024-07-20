package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.ChangePassRequest
import com.fidilaundry.app.model.request.ChangeProfileRequest
import com.fidilaundry.app.model.request.SaveNotifRequest
import com.fidilaundry.app.model.response.BaseObjResponse
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.NotifResponse
import com.fidilaundry.app.model.response.ProfileResponse
import com.fidilaundry.app.util.Constant

interface NotifRepository {
    suspend fun getNotif(userId: Int): UseCaseResult<NotifResponse>
    suspend fun saveNotif(req: SaveNotifRequest): UseCaseResult<BaseResponse>
}

class NotifRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) :
    NotifRepository {

    override suspend fun getNotif(userId: Int): UseCaseResult<NotifResponse> {
        return try {
            val result = api.getNotifList(paperPrefs.getToken(), userId)
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

    override suspend fun saveNotif(req: SaveNotifRequest): UseCaseResult<BaseResponse> {
        return try {
            val contentType = "application/json"
            val result = api.saveNotif(paperPrefs.getToken(), contentType, req)
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