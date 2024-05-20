package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.ComplaintRepository
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.ComplaintFeedbackRequest
import com.fidilaundry.app.model.request.UpdateOrderStatusRequest
import com.fidilaundry.app.model.request.UserComplaintRequest
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.ComplaintListResponse
import com.fidilaundry.app.model.response.UpdateStatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComplaintViewModel(private val complaintRepository: ComplaintRepository) : BaseViewModel() {

    val addUserComplaint = SingleLiveEvent<BaseResponse>()
    val updateOrderStatusResponse = SingleLiveEvent<UpdateStatusResponse>()
    val feedbackComplaintRes = SingleLiveEvent<BaseResponse>()
    val complaintRes = SingleLiveEvent<ComplaintListResponse>()
    val complaintCompleteRes = SingleLiveEvent<ComplaintListResponse>()

    val ctgValue = NonNullMutableLiveData("")
    val desc = NonNullMutableLiveData("")
    val descFeedback = NonNullMutableLiveData("")

    fun addUserComplaint(req: UserComplaintRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                complaintRepository.addUserComplaint(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> addUserComplaint.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> {
                    showError.value =
                        Utils.handleException(response.exception)
                }
            }
        }
    }

    fun updateOrderStatus(req: UpdateOrderStatusRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                complaintRepository.updateOrderStatus(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> updateOrderStatusResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value =
                        Utils.handleException(response.exception)
            }
        }
    }

    fun feedbackComplaint(req: ComplaintFeedbackRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                complaintRepository.feedbackComplaint(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> feedbackComplaintRes.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value =
                        Utils.handleException(response.exception)
            }
        }
    }

    fun getComplaintList(type: Int) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                complaintRepository.getComplaint()
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> {
                    when (type) {
                        1 -> complaintRes.value = response.data
                        2 -> complaintCompleteRes.value = response.data
                    }
                }
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value =
                    Utils.handleException(response.exception)
            }
        }
    }
}