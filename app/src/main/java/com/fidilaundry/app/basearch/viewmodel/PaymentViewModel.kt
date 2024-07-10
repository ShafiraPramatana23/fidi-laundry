package com.fidilaundry.app.basearch.viewmodel

import android.text.Editable
import com.fidilaundry.app.basearch.repository.OrderRepository
import com.fidilaundry.app.basearch.repository.PaymentRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.*
import com.fidilaundry.app.model.response.*
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentViewModel(private val paymentRepository: PaymentRepository, private val orderRepository: OrderRepository) : BaseViewModel() {

    val updateOrderStatusResponse = SingleLiveEvent<UpdateStatusResponse>()
    val createPaymentResponse = SingleLiveEvent<CreatePaymentResponse>()
    val updatePaymentResponse = SingleLiveEvent<BaseResponse>()
    val paymentListResponse = SingleLiveEvent<PaymentListResponse>()

    val paymentMethod = NonNullMutableLiveData("")

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

    fun createPayment(req: CreatePaymentRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                paymentRepository.createPayment(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> createPaymentResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }

    fun updatePayment(req: UpdatePaymentRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                paymentRepository.updatePayment(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> updatePaymentResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }

    fun getPaymentList(orderId: Int, custId: String, paymentType: String, status: String) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                paymentRepository.getPaymentList(orderId)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> paymentListResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }

    fun setPaymentMethod(pay: String) {
        paymentMethod.value = pay
    }
}