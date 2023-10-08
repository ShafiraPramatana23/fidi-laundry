package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.ChangePassRequest
import com.fidilaundry.app.model.request.ChangeProfileRequest
import com.fidilaundry.app.model.response.BaseObjResponse
import com.fidilaundry.app.model.response.ProfileResponse
import com.fidilaundry.app.util.Constant

interface ProfileRepository {
    suspend fun profile(): UseCaseResult<ProfileResponse>
    suspend fun changeProfile(req: ChangeProfileRequest): UseCaseResult<BaseObjResponse>
    suspend fun changePass(req: ChangePassRequest): UseCaseResult<BaseObjResponse>
}

class ProfileRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) :
    ProfileRepository {

    override suspend fun profile(): UseCaseResult<ProfileResponse> {
        return try {
            val result = api.profile(paperPrefs.getToken())
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

    override suspend fun changeProfile(req: ChangeProfileRequest): UseCaseResult<BaseObjResponse> {
        return try {
            val result = api.changeProfile(paperPrefs.getToken(), req)
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

    override suspend fun changePass(req: ChangePassRequest): UseCaseResult<BaseObjResponse> {
        return try {
            val result = api.changePass(paperPrefs.getToken(), req)
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