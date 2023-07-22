package com.fidilaundry.app.basearch.viewmodel

import androidx.room.FtsOptions.Order
import com.fidilaundry.app.basearch.repository.OrderRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.OrderRequest
import com.fidilaundry.app.model.response.BaseObjResponse
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.ItemListResponse
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderViewModel(private val orderRepository: OrderRepository) : BaseViewModel() {

    val itemsListResponse = SingleLiveEvent<ItemListResponse>()
    val orderResponse = SingleLiveEvent<BaseObjResponse>()

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
}