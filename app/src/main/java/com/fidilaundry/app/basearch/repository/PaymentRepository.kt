package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.request.*
import com.fidilaundry.app.model.response.*
import com.fidilaundry.app.util.Constant

interface PaymentRepository {
    suspend fun createPayment(req: CreatePaymentRequest): UseCaseResult<CreatePaymentResponse>
    suspend fun updatePayment(req: UpdatePaymentRequest): UseCaseResult<BaseResponse>
}

class PaymentRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) :
    PaymentRepository {

    override suspend fun createPayment(req: CreatePaymentRequest): UseCaseResult<CreatePaymentResponse> {
        return try {
            val contentType = "application/json"
            val result = api.createPaymentMidtrans(paperPrefs.getToken(), contentType, req)
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

    override suspend fun updatePayment(req: UpdatePaymentRequest): UseCaseResult<BaseResponse> {
        return try {
            val contentType = "application/json"
            val result = api.updatePaymentMidtrans(paperPrefs.getToken(), contentType, req)
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