package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.ComplaintRepository
import com.fidilaundry.app.basearch.repository.MasterRepository
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.model.response.BaseResponse

class ComplaintViewModel(private val complaintRepository: ComplaintRepository) : BaseViewModel() {

    val priceListResponse = SingleLiveEvent<BaseResponse>()
    val priceAddResponse = SingleLiveEvent<BaseResponse>()
    val priceUpdateResponse = SingleLiveEvent<BaseResponse>()
    val priceDeleteResponse = SingleLiveEvent<BaseResponse>()

    val itemsListResponse = SingleLiveEvent<BaseResponse>()
    val itemsAddResponse = SingleLiveEvent<BaseResponse>()
    val itemsUpdateResponse = SingleLiveEvent<BaseResponse>()
    val itemsDeleteResponse = SingleLiveEvent<BaseResponse>()

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