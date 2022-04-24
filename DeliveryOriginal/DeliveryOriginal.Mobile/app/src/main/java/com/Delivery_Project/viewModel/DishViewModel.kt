package com.Delivery_Project.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Delivery_Project.pojo.Category
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.repository.DishRepository
import com.Delivery_Project.repository.MenuRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DishViewModel constructor(private val repository: DishRepository): ViewModel(){
    val dishList = MutableLiveData<List<Dish>>()
    val errorMessage = MutableLiveData<String>()

    fun getDish() {
        val response = repository.getDish()
        response.enqueue(object : Callback<List<Dish>> {
            override fun onResponse(call: Call<List<Dish>>, response: Response<List<Dish>>) {
                dishList.postValue(response.body())
            }
            override fun onFailure(call: Call<List<Dish>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }


}