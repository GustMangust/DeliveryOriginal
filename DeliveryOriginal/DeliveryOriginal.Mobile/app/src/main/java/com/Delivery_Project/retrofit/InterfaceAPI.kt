package com.Delivery_Project.retrofit

import com.Delivery_Project.pojo.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface InterfaceAPI {
    @GET("Category/GetAll")
    fun getCategory():Call<List<Category>>

    @GET("Dish/GetAll")
    fun getDish():Call<List<Dish>>

    @GET("User/GetAll")
    fun getUser(): Call<List<User>>

    @GET("Dish/GetDayDishes")
    fun getRandomDish():Call<List<Dish>>

    @GET("Order/GetAll")
    fun getOrder():Call<List<Order>>

    @POST("Order/Add")
    suspend fun addOrder(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("User/Add")
    suspend fun addUser(@Body requestBody: RequestBody): Response<ResponseBody>


    companion object {
        var interfaceAPI: InterfaceAPI? = null

        fun getInstance() : InterfaceAPI {
            if (interfaceAPI == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://delivery-original-api.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                interfaceAPI = retrofit.create(InterfaceAPI::class.java)
            }
            return interfaceAPI!!
        }
    }

}