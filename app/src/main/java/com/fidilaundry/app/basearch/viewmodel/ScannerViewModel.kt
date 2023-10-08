package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.CustomerRepository
import com.fidilaundry.app.basearch.repository.OrderRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.OrderRequest
import com.fidilaundry.app.model.request.UpdateOrderStatusRequest
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.CustomerListResponse
import com.fidilaundry.app.model.response.RequestOrderResponse
import com.fidilaundry.app.model.response.UpdateStatusResponse
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScannerViewModel(private val customerRepository: CustomerRepository, private val orderRepository: OrderRepository) : BaseViewModel() {

    val custResponse = SingleLiveEvent<CustomerListResponse>()
    val orderResponse = SingleLiveEvent<RequestOrderResponse>()
    val updateOrderStatusResponse = SingleLiveEvent<UpdateStatusResponse>()

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
                is UseCaseResult.Error -> showError.value =
                    Utils.handleException(response.exception)
            }
        }
    }

    fun requestOrder(req: OrderRequest) {
        println("huhuhaha: "+req.order_for)

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
                    println("huhuy what: "+response.exception.message)
                    showError.value =
                        Utils.handleException(response.exception)
                }
            }
        }
    }

}