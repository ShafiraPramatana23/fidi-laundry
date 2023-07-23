package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.AuthRepository
import com.fidilaundry.app.basearch.repository.OrderRepository
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.OrderListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val authRepository: AuthRepository, private val orderRepository: OrderRepository) : BaseViewModel() {

    val orderListResponse = SingleLiveEvent<OrderListResponse>()

    fun getOrderList(custId: String, serviceId: String, step: String, status: String) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                orderRepository.getOrderList(custId, serviceId, step, status)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> orderListResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value =
                    Utils.handleException(response.exception)
            }
        }
    }
}