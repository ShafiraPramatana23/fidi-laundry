package com.fidilaundry.app.basearch.viewmodel

import android.text.Editable
import android.text.TextUtils
import com.fidilaundry.app.basearch.repository.AuthRepository
import com.fidilaundry.app.basearch.repository.MasterRepository
import com.fidilaundry.app.util.TextCheckerFormater
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MasterViewModel(private val masterRepository: MasterRepository) : BaseViewModel() {

    val loginRes = SingleLiveEvent<BaseResponse>()
    val isEnableButton = NonNullMutableLiveData(false)

    val ctgValue = NonNullMutableLiveData("")
    val itemsValue = NonNullMutableLiveData("")
    val itemsIdValue = NonNullMutableLiveData(0)
    val serviceValue = NonNullMutableLiveData("")
    val priceValue = NonNullMutableLiveData("")

//    fun onUsernameText(e: Editable) {
//        checkInput()
//    }
//
//    fun onPassword(e: Editable) {
//        checkInput()
//    }

//    fun checkInput() {
//        isEnableButton.value =
//                (!TextUtils.isEmpty(loginPassword.value) && !TextUtils.isEmpty(loginUsername.value))
//    }
}