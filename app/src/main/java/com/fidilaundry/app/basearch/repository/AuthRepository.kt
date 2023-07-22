package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.EndpointsNoAuth
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.LoginRequest
import com.fidilaundry.app.model.request.RegisterRequest
import com.fidilaundry.app.model.response.LoginResponse
import com.fidilaundry.app.util.Constant

interface AuthRepository {
    suspend fun login(req: LoginRequest): UseCaseResult<LoginResponse>
    suspend fun register(req: RegisterRequest): UseCaseResult<LoginResponse>
}

class AuthRepositoryImpl(private val api: EndpointsNoAuth, private val paperPrefs: PaperPrefs) :
    AuthRepository {

    override suspend fun login(req: LoginRequest): UseCaseResult<LoginResponse> {
        return try {
            val contentType = "application/json"
            val result = api.doLogin(contentType, req)
            when (result.status?.code) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.results?.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }!!
    }

    override suspend fun register(req: RegisterRequest): UseCaseResult<LoginResponse> {
        return try {
            val contentType = "application/json"
            val result = api.register(contentType, req)
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