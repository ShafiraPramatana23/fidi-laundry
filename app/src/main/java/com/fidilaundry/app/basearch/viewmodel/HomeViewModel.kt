package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.AuthRepository
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.model.response.BaseResponse

class HomeViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    val loginRes = SingleLiveEvent<BaseResponse>()
    val isEnableButton = NonNullMutableLiveData(false)

    private var vEmail = ""
    private var vPhoneNumber = ""
    private var vUsername = ""

    val loginPassword = NonNullMutableLiveData("")
    val loginUsername = NonNullMutableLiveData("")

}