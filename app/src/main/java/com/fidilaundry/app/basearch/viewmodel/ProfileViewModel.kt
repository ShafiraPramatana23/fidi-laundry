package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.ProfileRepository
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.ChangePassRequest
import com.fidilaundry.app.model.request.ChangeProfileRequest
import com.fidilaundry.app.model.response.BaseObjResponse
import com.fidilaundry.app.model.response.ProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(private val profileRepository: ProfileRepository) : BaseViewModel() {

    val passResponse = SingleLiveEvent<BaseObjResponse>()
    val changeProfileResponse = SingleLiveEvent<BaseObjResponse>()
    val profileResponse = SingleLiveEvent<ProfileResponse>()

    val oldPass = NonNullMutableLiveData("")
    val newPass = NonNullMutableLiveData("")
    val confirmNewPass = NonNullMutableLiveData("")
    val name = NonNullMutableLiveData("")
    val phone = NonNullMutableLiveData("")

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
    fun changeProfile() {
        showProgressLiveData.postValue(true)

        scope.launch {
            val response = withContext(Dispatchers.IO) {
                profileRepository.changeProfile(
                    ChangeProfileRequest(
                        name.value, phone.value
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
                    ChangePassRequest(newPass.value)
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