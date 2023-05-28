package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.model.response.BaseResponse

interface OrderRepository {
    suspend fun login(
        username: String, phone_number: String, email: String, password: String
    ): UseCaseResult<BaseResponse>
}

class OrderRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) :
    OrderRepository {

    override suspend fun login(
        username: String,
        phone_number: String,
        email: String,
        password: String
    ): UseCaseResult<BaseResponse> {
        return UseCaseResult.Failed("")
//        return try {
//            val result = api.doLogin(username, phone_number, email, password)
//            when (result.status) {
//                Constant.SUCCESSCODE -> {
//                    UseCaseResult.Success(result)
//                }
//                else -> {
//                    result.message?.let { UseCaseResult.Failed(it) }
//                }
//            }
//        }
//        catch (ex: Exception) {
//            when(ex) {
//                is HttpException -> {
//                    try {
//                        val data: String = ex.response()!!.errorBody()!!.string()
//                        val jObjError = JSONObject(data)
//                        val messagesError = jObjError.get("message") as String
//                        when {
//                            messagesError.contains("Account not found") -> UseCaseResult.Failed("Username Tidak Ditemukan")
//                            messagesError.contains("Ensure your Email and Password are correct") -> UseCaseResult.Failed("Username dan Password anda Salah")
//                            else -> UseCaseResult.Error(ex)
//                        }
//                    } catch (e: java.lang.Exception) {
//                        UseCaseResult.Error(e)
//                    }
//                }
//                else -> {
//                    if(ex.message!!.contains("498"))
//                        UseCaseResult.SessionTimeOut("tokenExpired")
//                    else
//                        UseCaseResult.Error(ex)
//                }
//            }
//        }!!
    }
}