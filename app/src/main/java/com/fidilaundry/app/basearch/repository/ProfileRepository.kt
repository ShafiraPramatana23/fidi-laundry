package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.response.ProfileResponse
import com.fidilaundry.app.util.Constant

interface ProfileRepository {
    suspend fun profile(): UseCaseResult<ProfileResponse>
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
}