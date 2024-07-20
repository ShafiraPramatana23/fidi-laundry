package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.NotifRepository
import com.fidilaundry.app.basearch.repository.ProfileRepository
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.request.ChangePassRequest
import com.fidilaundry.app.model.request.ChangeProfileRequest
import com.fidilaundry.app.model.request.SaveNotifRequest
import com.fidilaundry.app.model.response.BaseObjResponse
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.model.response.NotifResponse
import com.fidilaundry.app.model.response.ProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotifViewModel(private val notifRepository: NotifRepository) : BaseViewModel() {

    val notifResponse = SingleLiveEvent<NotifResponse>()
    val saveNotifResponse = SingleLiveEvent<BaseResponse>()

    fun getNotif(userId: Int) {
        launch {
            val response = withContext(Dispatchers.IO) { notifRepository.getNotif(userId) }
            when (response) {
                is UseCaseResult.Success -> notifResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
                else -> {}
            }
        }
    }

    fun saveNotif(req: SaveNotifRequest) {
        launch {
            val response = withContext(Dispatchers.IO) { notifRepository.saveNotif(req) }
            when (response) {
                is UseCaseResult.Success -> saveNotifResponse.value = response.data
                is UseCaseResult.Failed -> showError.value = response.errorMessage
                is UseCaseResult.Error -> showError.value = Utils.handleException(response.exception)
                else -> {}
            }
        }
    }
}