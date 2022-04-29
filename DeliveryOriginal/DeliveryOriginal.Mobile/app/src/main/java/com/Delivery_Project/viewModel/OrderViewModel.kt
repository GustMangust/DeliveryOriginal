package com.Delivery_Project.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Delivery_Project.pojo.Category
import com.Delivery_Project.pojo.Order
import com.Delivery_Project.repository.MenuRepository
import com.Delivery_Project.repository.OrderRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewModel constructor(private val repository: OrderRepository): ViewModel(){
    val orderList = MutableLiveData<List<Order>>()
    val errorMessage = MutableLiveData<String>()

    fun getOrder() {
        val response = repository.getOrder()
        response.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                orderList.postValue(response.body())
            }
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }


}