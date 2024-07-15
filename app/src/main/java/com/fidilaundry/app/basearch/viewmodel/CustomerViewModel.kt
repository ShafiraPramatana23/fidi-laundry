package com.fidilaundry.app.basearch.viewmodel

import android.text.Editable
import android.text.TextUtils
import com.fidilaundry.app.basearch.repository.AuthRepository
import com.fidilaundry.app.basearch.repository.CustomerRepository
import com.fidilaundry.app.basearch.repository.ProfileRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.AddCustomerRequest
import com.fidilaundry.app.model.request.ChangePassRequest
import com.fidilaundry.app.model.request.ChangeProfileRequest
import com.fidilaundry.app.model.request.RegisterRequest
import com.fidilaundry.app.model.response.*
import com.fidilaundry.app.util.TextCheckerFormater
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomerViewModel(private val customerRepository: CustomerRepository, private val profileRepository: ProfileRepository) : BaseViewModel() {

    val addResponse = SingleLiveEvent<BaseResponse>()
    val custResponse = SingleLiveEvent<CustomerListResponse>()
    val changeProfileResponse = SingleLiveEvent<BaseObjResponse>()
    val passResponse = SingleLiveEvent<BaseObjResponse>()

    val userId = NonNullMutableLiveData(0)
    val fullname = NonNullMutableLiveData("")
    val email = NonNullMutableLiveData("")
    val phone = NonNullMutableLiveData("")
    val password = NonNullMutableLiveData("")

    val isEnableButton = NonNullMutableLiveData(false)

    fun addCustomer() {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                customerRepository.addCustomer(
                    AddCustomerRequest(
                        fullname.value, email.value, phone.value, password.value
                    )
                )
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> addResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value =
                    Utils.handleException(response.exception)
            }
        }
    }

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

    fun changeProfile() {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                profileRepository.changeProfile(
                    ChangeProfileRequest(
                        userId.value, fullname.value, phone.value, email.value
                    )
                )
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> changeProfileResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }

    fun changePass() {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                profileRepository.changePass(
                    ChangePassRequest(userId.value, password.value)
                )
            }

            showProgressLiveData.postValue(false)
            when (response) {
                is UseCaseResult.Success -> passResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
            }
        }
    }
}