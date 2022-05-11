package com.Delivery_Project.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.model.CartModel
import com.Delivery_Project.pojo.Category
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.pojo.Order
import com.Delivery_Project.pojo.User
import com.Delivery_Project.repository.MenuRepository
import com.Delivery_Project.repository.OrderRepository
import com.Delivery_Project.retrofit.InterfaceAPI
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

    fun requestOrder(dateTime: String, status: Int, address: String, phoneNumber:String, customer: User, dishes: ArrayList<Dish>){
        val jsonObject = JSONObject()
        val jsonDish =  Gson().toJsonTree(dishes) as JsonArray
        val jsonCustomer = Gson().toJsonTree(customer)

        jsonObject.put("PhoneNumber", phoneNumber)
        jsonObject.put("SubmittedAt", dateTime)
        jsonObject.put("Status", status)
        jsonObject.put("Address", address)
        jsonObject.put("Customer", jsonCustomer)
        jsonObject.put("CurrentEmployee", null)
        jsonObject.put("Dishes", jsonDish)
        val jsonObjectString = jsonObject.toString()

        Log.d("Pretty Printed JSON :", jsonObjectString.toString())

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = InterfaceAPI.getInstance().addOrder(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.code().toString()
                        )
                    )
                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }
     fun addOrder(databaseHelper: DatabaseHelper, context: Context, dish: Dish){
        val dishId = dish.Id
        val name  = dish.Name
        val image: String? = dish.ImageUrl
        val description = dish.Description
        val category = dish.Category
        val cost = dish.Cost

        val order = CartModel(dishId = dishId,name = name, image = image, description = description,category = category,  cost = cost)
        val status = databaseHelper.insertOrder(order)

        if(status > -1){
            Toast.makeText(context, "Dish added!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Dish not added!", Toast.LENGTH_SHORT).show()
        }
    }
}