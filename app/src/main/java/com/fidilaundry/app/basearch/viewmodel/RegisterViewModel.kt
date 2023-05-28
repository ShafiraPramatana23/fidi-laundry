package com.fidilaundry.app.basearch.viewmodel

import android.text.Editable
import android.text.TextUtils
import com.fidilaundry.app.basearch.repository.AuthRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.RegisterRequest
import com.fidilaundry.app.model.response.LoginResponse
import com.fidilaundry.app.util.TextCheckerFormater
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    val registerResponse = SingleLiveEvent<LoginResponse>()
    val vlPassword = NonNullMutableLiveData("")
    val vlPasswordConfirmation = NonNullMutableLiveData("")
    val vlUsername = NonNullMutableLiveData("")
    val vlPhoneNumber = NonNullMutableLiveData("")
    val vlEmail = NonNullMutableLiveData("")
    val vlName = NonNullMutableLiveData("")
    val isEnableButton = NonNullMutableLiveData(false)

    fun register() {
        showProgressLiveData.postValue(true)
        scope.launch {
            if (!TextCheckerFormater.isValidFullname(vlName.value)) {
                showProgressLiveData.postValue(false)
                showError.value = "invalidLengthFullname"
            } else if (!TextCheckerFormater.isValidEmail(vlEmail.value)) {
                showProgressLiveData.postValue(false)
                showError.value = "invalidEmail"
            } else if (!TextCheckerFormater.isValidPhoneFormat(vlPhoneNumber.value)) {
                showProgressLiveData.postValue(false)
                showError.value = "invalidPhoneFormat"
            } else if (!TextCheckerFormater.isValidPhoneNumber(vlPhoneNumber.value)) {
                showProgressLiveData.postValue(false)
                showError.value = "invalidPhone"
            } else if (!TextCheckerFormater.isValidPasswordLength(vlPassword.value)) {
                showProgressLiveData.postValue(false)
                showError.value = "invalidLengthPass"
            } else if (!TextCheckerFormater.isValidPassword(vlPassword.value)) {
                showProgressLiveData.postValue(false)
                showError.value = "invalidFormatPass"
            }  else if (vlPassword.value != vlPasswordConfirmation.value) {
                showProgressLiveData.postValue(false)
                showError.value = "invalidConfirmPass"
            } else {
                val response = withContext(Dispatchers.IO) {
                    authRepository.register(
                        RegisterRequest(
                            vlName.value,
                            vlEmail.value,
                            vlPhoneNumber.value,
                            vlPassword.value
                        )
                    )
                }

                showProgressLiveData.postValue(false)
                when (response) {
                    is UseCaseResult.Success -> registerResponse.value = response.data
                    is UseCaseResult.Failed -> showError.value = response.errorMessage
                    is UseCaseResult.SessionTimeOut -> showSessionTimeOut.value = response.errorMessage
                    is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
                }
            }
        }
    }

    fun onName(e: Editable) {
        vlName.value = e.toString()
        checkInput()
    }

    fun onEmail(e: Editable) {
        vlEmail.value = e.toString()
        checkInput()
    }

    fun onPhone(e: Editable) {
        vlPhoneNumber.value = e.toString()
        checkInput()
    }

    fun onPass(e: Editable) {
        vlPassword.value = e.toString()
        checkInput()
    }

    fun onPassConf(e: Editable) {
        vlPasswordConfirmation.value = e.toString()
        checkInput()
    }

    fun checkInput() {
        isEnableButton.value =
            (!TextUtils.isEmpty(vlName.value) && !TextUtils.isEmpty(vlEmail.value)
                    && !TextUtils.isEmpty(vlPhoneNumber.value)
                    && !TextUtils.isEmpty(vlPassword.value)
                    && !TextUtils.isEmpty(vlPasswordConfirmation.value))
    }

}