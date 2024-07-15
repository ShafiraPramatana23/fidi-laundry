package com.fidilaundry.app.basearch.viewmodel

import android.text.Editable
import android.text.TextUtils
import com.fidilaundry.app.basearch.repository.AuthRepository
import com.fidilaundry.app.basearch.repository.ProfileRepository
import com.fidilaundry.app.util.TextCheckerFormater
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.LoginRequest
import com.fidilaundry.app.model.response.LoginResponse
import com.fidilaundry.app.model.response.ProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val authRepository: AuthRepository, private val profileRepository: ProfileRepository) : BaseViewModel() {

    val loginResponse = SingleLiveEvent<LoginResponse>()
    val profileResponse = SingleLiveEvent<ProfileResponse>()

    val isEnableButton = NonNullMutableLiveData(false)

    private var vEmail = ""
    private var vPhoneNumber = ""

    val loginPassword = NonNullMutableLiveData("")
    val loginUsername = NonNullMutableLiveData("")

    fun loginInit(deviceId: String) {
        showProgressLiveData.postValue(true)

        launch {
            if (!TextUtils.isEmpty(loginUsername.value) && !TextUtils.isEmpty(loginPassword.value)) {
                when {
                    TextCheckerFormater.isValidEmail(loginUsername.value) -> {
                        vEmail = loginUsername.value
                        vPhoneNumber = ""
                    }
                    TextCheckerFormater.isValidPhoneNumber(loginUsername.value) -> {
                        vEmail = ""
                        vPhoneNumber = loginUsername.value
                    }
                }

                val response = withContext(Dispatchers.IO) {
                    authRepository.login(
                        LoginRequest(
                            if (vEmail.isNotEmpty()) vEmail else vPhoneNumber,
                            loginPassword.value, deviceId
                        )
                    )
                }

                showProgressLiveData.postValue(false)

                when (response) {
                    is UseCaseResult.Success -> loginResponse.value = response.data
                    is UseCaseResult.Failed -> showError.value = response.errorMessage
                    is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
                    else -> {}
                }
            } else {
                showProgressLiveData.postValue(false)
                when {
                    (TextUtils.isEmpty(loginUsername.value) && TextUtils.isEmpty(loginPassword.value)) -> showError.value = "emptyAll"
                    TextUtils.isEmpty(loginPassword.value) -> showError.value = "emptyPass"
                }
            }
        }
    }

    fun getProfile() {
        launch {
            val response = withContext(Dispatchers.IO) { profileRepository.profile() }
            when (response) {
                is UseCaseResult.Success -> profileResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
                else -> {}
            }
        }
    }

    fun onUsernameText(e: Editable) {
        checkInput()
    }

    fun onPassword(e: Editable) {
        checkInput()
    }

    fun checkInput() {
        isEnableButton.value =
                (!TextUtils.isEmpty(loginPassword.value) && !TextUtils.isEmpty(loginUsername.value))
    }
}