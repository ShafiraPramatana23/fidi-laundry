package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.network.EndpointsNoAuth
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.AddCustomerRequest
import com.fidilaundry.app.model.request.LoginRequest
import com.fidilaundry.app.model.request.RegisterRequest
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.CustomerListResponse
import com.fidilaundry.app.model.response.LoginResponse
import com.fidilaundry.app.util.Constant

interface CustomerRepository {
    suspend fun addCustomer(req: AddCustomerRequest): UseCaseResult<BaseResponse>
    suspend fun getCustomer(): UseCaseResult<CustomerListResponse>
}

class CustomerRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) :
    CustomerRepository {

    override suspend fun addCustomer(req: AddCustomerRequest): UseCaseResult<BaseResponse> {
        return try {
            val contentType = "application/json"
            val result = api.addCustomer(paperPrefs.getToken(), contentType, req)
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

    override suspend fun getCustomer(): UseCaseResult<CustomerListResponse> {
        return try {
            val result = api.getCustomerList(paperPrefs.getToken())
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