package com.Delivery_Project.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.factory.DishViewModelFactory
import com.Delivery_Project.factory.MenuViewModelFactory
import com.Delivery_Project.pojo.Category
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.pojo.User
import com.Delivery_Project.repository.DishRepository
import com.Delivery_Project.repository.MenuRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.ui.ui.fragment.MenuFragment
import com.Delivery_Project.utility.ConnectionUtility
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
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