package com.Delivery_Project.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Delivery_Project.pojo.Category
import com.Delivery_Project.repository.MenuRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuViewModel constructor(private val repository: MenuRepository): ViewModel(){
    val categoryList = MutableLiveData<List<Category>>()
    val errorMessage = MutableLiveData<String>()

    fun getCategory() {
        val response = repository.getCategory()
        response.enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                categoryList.postValue(response.body())
            }
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }


}