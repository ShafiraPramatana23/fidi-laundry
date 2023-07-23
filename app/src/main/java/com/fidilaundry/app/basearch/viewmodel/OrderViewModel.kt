package com.fidilaundry.app.basearch.viewmodel

import androidx.room.FtsOptions.Order
import com.fidilaundry.app.basearch.repository.OrderRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.OrderRequest
import com.fidilaundry.app.model.request.UpdateOrderRequest
import com.fidilaundry.app.model.response.*
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderViewModel(private val orderRepository: OrderRepository) : BaseViewModel() {

    val itemsListResponse = SingleLiveEvent<ItemListResponse>()
    val orderResponse = SingleLiveEvent<BaseObjResponse>()
    val updateOrderResponse = SingleLiveEvent<BaseResponse>()
    val orderListResponse = SingleLiveEvent<OrderListResponse>()
    val orderDetailResponse = SingleLiveEvent<OrderDetailResponse>()

    val category = NonNullMutableLiveData("")
    val categoryId = NonNullMutableLiveData("")
    val service = NonNullMutableLiveData("")
    val serviceId = NonNullMutableLiveData("")
    val kiloan = NonNullMutableLiveData("")

    fun getItemsByService(id: Int) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                orderRepository.getItemByService(id)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> itemsListResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value =
                    Utils.handleException(response.exception)
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
                is UseCaseResult.Error -> showError.value =
                    Utils.handleException(response.exception)
            }
        }
    }

    fun updateOrder(req: UpdateOrderRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                orderRepository.updateOrder(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> updateOrderResponse.value = response.data
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

    fun getOrderDetail(id: String) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                orderRepository.getOrderDetail(id)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> orderDetailResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value =
                    Utils.handleException(response.exception)
            }
        }
    }
}