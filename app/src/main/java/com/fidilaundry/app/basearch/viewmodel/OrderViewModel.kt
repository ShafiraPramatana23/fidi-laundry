package com.fidilaundry.app.basearch.viewmodel

import android.text.Editable
import com.fidilaundry.app.basearch.repository.OrderRepository
import com.fidilaundry.app.basearch.repository.PaymentRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.OrderRequest
import com.fidilaundry.app.model.request.SendNotifRequest
import com.fidilaundry.app.model.request.UpdateOrderRequest
import com.fidilaundry.app.model.request.UpdateOrderStatusRequest
import com.fidilaundry.app.model.response.*
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderViewModel(private val orderRepository: OrderRepository, private val paymentRepository: PaymentRepository) : BaseViewModel() {

    val itemsListResponse = SingleLiveEvent<ItemListResponse>()
    val orderResponse = SingleLiveEvent<RequestOrderResponse>()
    val updateOrderResponse = SingleLiveEvent<BaseResponse>()
    val updateOrderStatusResponse = SingleLiveEvent<UpdateStatusResponse>()
    val orderListResponse = SingleLiveEvent<OrderListResponse>()
    val orderDetailResponse = SingleLiveEvent<OrderDetailResponse>()
    val sendNotifResponse = SingleLiveEvent<BaseObjResponse>()
    val paymentResponse = SingleLiveEvent<PaymentListResponse>()
    val orderListCustResponse = SingleLiveEvent<OrderListResponse>()

    val categoryId = NonNullMutableLiveData("")
    val service = NonNullMutableLiveData("")
    val serviceId = NonNullMutableLiveData("")
    val kiloan = NonNullMutableLiveData("")
    val stepping = NonNullMutableLiveData("")

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
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
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

    fun getPayment(orderId: Int) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                paymentRepository.getPaymentList(orderId)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> paymentResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }

    fun getOrderListCust(custId: String, serviceId: String, step: String, status: String) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                orderRepository.getOrderListCust(custId, serviceId, step, status)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> orderListCustResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value =
                    Utils.handleException(response.exception)
            }
        }
    }

    fun onKiloan(e: Editable) {
        kiloan.value = e.toString()
    }
}