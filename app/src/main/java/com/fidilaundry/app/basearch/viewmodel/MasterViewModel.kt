package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.MasterRepository
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.AddItemRequest
import com.fidilaundry.app.model.request.DeleteItemRequest
import com.fidilaundry.app.model.request.UpdateItemRequest
import com.fidilaundry.app.model.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MasterViewModel(private val masterRepository: MasterRepository) : BaseViewModel() {

    val serviceResponse = SingleLiveEvent<ServiceListResponse>()
    val categoryResponse = SingleLiveEvent<CategoryListResponse>()
    val itemsListResponse = SingleLiveEvent<ItemListResponse>()
    val itemsAddResponse = SingleLiveEvent<BaseObjResponse>()
    val itemsUpdateResponse = SingleLiveEvent<BaseObjResponse>()
    val itemsDeleteResponse = SingleLiveEvent<BaseObjResponse>()

    val ctgValue = NonNullMutableLiveData("")
    val ctgIdValue = NonNullMutableLiveData("")
    val itemsValue = NonNullMutableLiveData("")
    val serviceValue = NonNullMutableLiveData("")
    val serviceIdValue = NonNullMutableLiveData("")
    val priceValue = NonNullMutableLiveData("")

    fun getService() {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                masterRepository.getService()
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> serviceResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }
    fun getCategory() {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                masterRepository.getCategory()
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> categoryResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }

    fun getItems() {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                masterRepository.getItem()
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

    fun addItems(req: AddItemRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                masterRepository.addItem(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> itemsAddResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }

    fun updateItems(req: UpdateItemRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                masterRepository.updateItem(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> itemsUpdateResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }

    fun deleteItems(req: DeleteItemRequest) {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                masterRepository.deleteItem(req)
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> itemsDeleteResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }
}