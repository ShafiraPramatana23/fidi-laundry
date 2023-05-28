package com.fidilaundry.app.basearch.viewmodel

import com.fidilaundry.app.basearch.repository.OrderRepository
import com.fidilaundry.app.basearch.util.SingleLiveEvent
import com.fidilaundry.app.model.response.BaseResponse
import com.fidilaundry.app.util.livedata.NonNullMutableLiveData

class OrderViewModel(private val orderRepository: OrderRepository) : BaseViewModel() {

    val orderResponse = SingleLiveEvent<BaseResponse>()
    val category = NonNullMutableLiveData("")
    val categoryId = NonNullMutableLiveData("")
    val service = NonNullMutableLiveData("")
    val serviceId = NonNullMutableLiveData("")
    val kiloan = NonNullMutableLiveData("")

}