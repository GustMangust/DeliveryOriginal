package com.Delivery_Project.ui.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.constants.Constants
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.FIRST_STATUS
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.databinding.ActivityCheckoutBinding
import com.Delivery_Project.factory.OrderViewModelFactory
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.pojo.User
import com.Delivery_Project.repository.OrderRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.retrofit.InterfaceAPI.Companion.interfaceAPI
import com.Delivery_Project.utility.ConnectionUtility
import com.Delivery_Project.viewModel.DishViewModel
import com.Delivery_Project.viewModel.OrderViewModel
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
    private lateinit var cartHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        var dishes = intent.getSerializableExtra("Dishes") as ArrayList<Dish>
        var user = intent.getSerializableExtra("User") as User
        val view = binding.root
        setContentView(view)
        var checkoutBtn = binding.checkoutBtn.setOnClickListener {
            val phoneNumber = binding.phone.text.toString().trim()
            val city = binding.city.text.toString().trim()
            val street = binding.Street.text.toString().trim()
            val house = binding.House.text.toString().trim()
            val apartment = binding.Apartment.text.toString().trim()
            val currentEmployee: User ?= null
            val customer: User = user
            val address  = "$city, $street $house, $apartment"
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm")
            val viewModel: OrderViewModel = ViewModelProvider(this, OrderViewModelFactory(
                OrderRepository(interfaceAPI!!))).get(OrderViewModel::class.java)
            val currentDate = sdf.format(Date())
            if(ConnectionUtility.isOnline(this)){
                viewModel.requestOrder(currentDate, FIRST_STATUS, address, phoneNumber, customer,dishes)
                cartHelper = DatabaseHelper(this)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("User", user)
                cartHelper.cleanCart()
                Toast.makeText(this, "Your order have been submitted successfully!", Toast.LENGTH_LONG).show()
                ContextCompat.startActivity(this, intent, null)
            } else {
                Toast.makeText(this, "Check your internet connection!", Toast.LENGTH_LONG).show()
            }
        }
    }

}