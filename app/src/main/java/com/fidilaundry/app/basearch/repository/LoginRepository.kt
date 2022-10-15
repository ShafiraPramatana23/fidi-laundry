package com.fidilaundry.app.basearch.repository

import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.basearch.network.Endpoints


interface LoginRepository {
    /*suspend fun loginInit(
        lat: String,
        lang: String,
        city: String,
        region: String,
        country: String,
        ipaddress: String,
            username: String?,
            phone_number: String?,
            email: String?,
            password: String
    ): UseCaseResult<LoginResponse>*/
}

class LoginRepositoryImpl(private val api: Endpoints, private val paperPrefs: PaperPrefs) : LoginRepository {

    /*override suspend fun loginInit(
        lat: String,
        lang: String,
        city: String,
        region: String,
        country: String,
        ipaddress: String,
            username: String?,
            phone_number: String?,
            email: String?,
            password: String
    ): UseCaseResult<LoginResponse> {
        val playerId = getOneSignalUserID()

        return try {
            val result = apiNoAuth.doLogin(lat,lang,city,region,country,ipaddress,  username, phone_number, email, password, playerId)
            when (result.status) {
                Constant.SUCCESSCODE -> {
                    UseCaseResult.Success(result)
                }
                else -> {
                    result.message?.let { UseCaseResult.Failed(it) }
                }
            }
        }
        catch (ex: Exception) {
            when(ex) {
                is HttpException -> {
                    try {
                        val data: String = ex.response()!!.errorBody()!!.string()
                        val jObjError = JSONObject(data)
                        val messagesError = jObjError.get("message") as String
                        when {
                            messagesError.contains("Account not found") -> UseCaseResult.Failed("Username Tidak Ditemukan")
                            messagesError.contains("Ensure your Email and Password are correct") -> UseCaseResult.Failed("Username dan Password anda Salah")
                            else -> UseCaseResult.Error(ex)
                        }
                    } catch (e: java.lang.Exception) {
                        UseCaseResult.Error(e)
                    }
                }
                else -> {
                    if(ex.message!!.contains("498"))
                        UseCaseResult.SessionTimeOut("tokenExpired")
                    else
                        UseCaseResult.Error(ex)
                }
            }
        }!!
    }*/
}