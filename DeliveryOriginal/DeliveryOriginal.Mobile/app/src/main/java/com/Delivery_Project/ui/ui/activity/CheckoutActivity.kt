package com.Delivery_Project.ui.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.Delivery_Project.databinding.ActivityCheckoutBinding
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.pojo.User
import com.Delivery_Project.retrofit.InterfaceAPI
import com.google.gson.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        var dishes = intent.getSerializableExtra("Dishes") as ArrayList<Dish>
        var user = intent.getSerializableExtra("User") as User
        val view = binding.root
        setContentView(view)
        var checkoutBtn = binding.checkoutBtn.setOnClickListener {
            val name = binding.fullNameCheckout.text.toString().trim()
            val phoneNumber = binding.phone.text.toString().trim()
            val city = binding.city.text.toString().trim()
            val street = binding.Street.text.toString().trim()
            val house = binding.House.text.toString().trim()
            val apartment = binding.Apartment.text.toString().trim()
            val status = 0
            val currentEmployee: User ?= null
            val customer: User = user
            val address  = "$city, $street $house, $apartment"
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm")
            val currentDate = sdf.format(Date())
            requestOrder(currentDate, status, address, customer,dishes)

        }
    }
    private fun requestOrder(dateTime: String, status: Int, address: String, customer: User,  dishes: ArrayList<Dish>){
        val jsonObject = JSONObject()
        val jsonDish =  Gson().toJsonTree(dishes) as JsonArray
        val jsonCustomer = Gson().toJsonTree(customer)
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
}