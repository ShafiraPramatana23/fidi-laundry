package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.OrderRepository
import com.fidilaundry.app.basearch.repository.TrackingRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.AddTrackingRequest
import com.fidilaundry.app.model.request.UpdateOrderStatusRequest
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.TrackingListResponse
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackingViewModel(
    private val trackingRepository: TrackingRepository,
    private val orderRepository: OrderRepository
) : BaseViewModel() {

    val addTrackResponse = SingleLiveEvent<BaseResponse>()
    val trackResponse = SingleLiveEvent<TrackingListResponse>()
    val updateOrderStatusResponse = SingleLiveEvent<BaseResponse>()

    val desc = NonNullMutableLiveData("")

    fun addTracking(req: AddTrackingRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                trackingRepository.addTracking(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> addTrackResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value =
                    Utils.handleException(response.exception)
            }
        }
    }

    fun updateOrderStatus(req: UpdateOrderStatusRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                orderRepository.updateOrderStatus(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> updateOrderStatusResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> {
                    println("huhuy what: " + response.exception.message)
                    showError.value =
                        Utils.handleException(response.exception)
                }
            }
        }
    }

    fun getTrackingList(id: Int) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                trackingRepository.getTracking(id)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> trackResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value =
                    Utils.handleException(response.exception)
            }
        }
    }
}