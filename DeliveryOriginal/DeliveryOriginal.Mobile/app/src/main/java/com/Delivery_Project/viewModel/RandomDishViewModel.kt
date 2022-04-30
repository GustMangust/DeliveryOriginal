package com.Delivery_Project.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Delivery_Project.pojo.Category
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.repository.MenuRepository
import com.Delivery_Project.repository.RandomDishRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomDishViewModel constructor(private val repository:RandomDishRepository): ViewModel(){
    val randomDishList = MutableLiveData<List<Dish>>()
    val errorMessage = MutableLiveData<String>()

    fun getRandomDish() {
        val response = repository.getRandomDish()
        response.enqueue(object : Callback<List<Dish>> {
            override fun onResponse(call: Call<List<Dish>>, response: Response<List<Dish>>) {
                randomDishList.postValue(response.body())
            }
            override fun onFailure(call: Call<List<Dish>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }


}