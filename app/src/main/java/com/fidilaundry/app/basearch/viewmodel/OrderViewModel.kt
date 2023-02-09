package com.fidilaundry.app.basearch.viewmodel

import android.text.Editable
import android.text.TextUtils
import com.esotericsoftware.minlog.Log.info
import com.fidilaundry.app.basearch.repository.AuthRepository
import com.fidilaundry.app.basearch.repository.OrderRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.basearch.util.UseCaseResult
import com.fidilaundry.app.basearch.util.Utils
import com.fidilaundry.app.model.BaseResponse
import com.fidilaundry.app.util.TextCheckerFormater
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderViewModel(private val orderRepository: OrderRepository) : BaseViewModel() {

    val orderResponse = SingleLiveEvent<BaseResponse>()
    val category = NonNullMutableLiveData("")
    val categoryId = NonNullMutableLiveData("")
    val service = NonNullMutableLiveData("")
    val serviceId = NonNullMutableLiveData("")
    val kiloan = NonNullMutableLiveData("")

}