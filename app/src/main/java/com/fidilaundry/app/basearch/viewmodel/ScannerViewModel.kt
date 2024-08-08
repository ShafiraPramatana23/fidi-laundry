package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.CustomerRepository
import com.fidilaundry.app.basearch.repository.OrderRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.OrderRequest
import com.fidilaundry.app.model.request.SendNotifRequest
import com.fidilaundry.app.model.request.UpdateOrderStatusRequest
import com.fidilaundry.app.model.response.BaseObjResponse
import com.fidilaundry.app.model.response.CustomerListResponse
import com.fidilaundry.app.model.response.RequestOrderResponse
import com.fidilaundry.app.model.response.UpdateStatusResponse
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScannerViewModel(private val customerRepository: CustomerRepository, private val orderRepository: OrderRepository) : BaseViewModel() {

    val custResponse = SingleLiveEvent<CustomerListResponse>()
    val orderResponse = SingleLiveEvent<RequestOrderResponse>()
    val updateOrderStatusResponse = SingleLiveEvent<UpdateStatusResponse>()
    val sendNotifResponse = SingleLiveEvent<BaseObjResponse>()

    val service = NonNullMutableLiveData("")
    val serviceId = NonNullMutableLiveData("")
    val custId = NonNullMutableLiveData("")

    fun getCustomer() {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                customerRepository.getCustomer()
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> custResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }

    fun requestOrder(req: OrderRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                orderRepository.requestOrder(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> orderResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
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
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }

    fun sendNotif(req: SendNotifRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                orderRepository.sendNotif(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> sendNotifResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = "errSendNotif"
                is UseCaseResult.SessionTimeOut -> showError.value = "errSendNotif"
                is UseCaseResult.Error -> showError.value = "errSendNotif"
            }
        }
    }
}