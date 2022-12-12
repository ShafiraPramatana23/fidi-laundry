package com.fidilaundry.app.basearch.viewmodel

import android.text.Editable
import android.text.TextUtils
import com.fidilaundry.app.basearch.repository.AuthRepository
import com.fidilaundry.app.util.TextCheckerFormater
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    val loginRes = SingleLiveEvent<BaseResponse>()
    val isEnableButton = NonNullMutableLiveData(false)

    private var vEmail = ""
    private var vPhoneNumber = ""
    private var vUsername = ""

    val loginPassword = NonNullMutableLiveData("")
    val loginUsername = NonNullMutableLiveData("")

    fun loginInit() {
        showProgressLiveData.postValue(true)

        launch {
            if (!TextUtils.isEmpty(loginUsername.value) && !TextUtils.isEmpty(loginPassword.value)) {
                when {
                    TextCheckerFormater.isValidEmail(loginUsername.value) -> {
                        vEmail = loginUsername.value
                        vPhoneNumber = ""
                        vUsername = ""
                    }
                    TextCheckerFormater.isValidPhoneNumber(loginUsername.value) -> {
                        vEmail = ""
                        vPhoneNumber = loginUsername.value
                        vUsername = ""
                    }
                    else -> {
                        vEmail = ""
                        vPhoneNumber = ""
                        vUsername = loginUsername.value
                    }
                }

                val response = withContext(Dispatchers.IO) {
                    authRepository.login(
                        (if(vUsername == "") null else vUsername)!!,
                        (if(vPhoneNumber == "") null else vPhoneNumber)!!,
                        (if(vEmail == "") null else vEmail)!!,
                        loginPassword.value
                    )
                }

                showProgressLiveData.postValue(false)

                when (response) {
                    is UseCaseResult.Success -> loginRes.value = response.data
                    is UseCaseResult.Failed -> {
                        if (response.errorMessage.equals(
                                "username dan password anda salah",
                                true
                            )
                        ) {
                            showError.value = "wrong"
                        } else if (response.errorMessage.equals(
                                "Username tidak ditemukan",
                                true
                            )
                        ) {
                            showError.value = "notFound"
                        } else if (response.errorMessage.equals(
                                "Email or username not found!",
                                true
                            )
                        ) {
                            showError.value = "emailUsernameNotFound"
                        } else {
                            showError.value = response.errorMessage
                        }
                    }
                    is UseCaseResult.Error -> {

                        if (response.exception.message?.contains("403")!!) {
                            showError.value = "notActive"
                        }
                        else if (response.exception.message?.contains("422")!!) {
                            showError.value = "wrongFormat"
                        }
                        else {
                            showError.value =
                                Utils.handleException(response.exception)
                        }
                    }
                }
            } else {
                showProgressLiveData.postValue(false)
                when {
                    (TextUtils.isEmpty(loginUsername.value) && TextUtils.isEmpty(loginPassword.value)) -> {
                        showError.value = "emptyAll"
                    }
                    TextUtils.isEmpty(loginUsername.value) -> {
                        showError.value = "emptyUname"
                    }
                    TextUtils.isEmpty(loginPassword.value) -> {
                        showError.value = "emptyPass"
                    }
                }
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