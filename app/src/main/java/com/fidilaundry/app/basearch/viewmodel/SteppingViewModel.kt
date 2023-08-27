package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.OrderRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.UpdateOrderStatusRequest
import com.fidilaundry.app.model.response.*
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SteppingViewModel(private val orderRepository: OrderRepository) : BaseViewModel() {

    val updateOrderStatusResponse = SingleLiveEvent<UpdateStatusResponse>()

    val stepping = NonNullMutableLiveData("")

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
                    println("huhuy what: "+response.exception.message)
                    showError.value =
                        Utils.handleException(response.exception)
                }
            }
        }
    }

}