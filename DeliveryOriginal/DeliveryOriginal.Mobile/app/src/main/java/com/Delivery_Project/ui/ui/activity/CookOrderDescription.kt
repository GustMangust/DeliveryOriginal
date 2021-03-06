package com.Delivery_Project.ui.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.SyncStateContract
import androidx.appcompat.app.AppCompatActivity
import com.Delivery_Project.R
import com.Delivery_Project.adapter.DishAdapter
import com.Delivery_Project.adapter.UserOrderDescriptionAdapter
import com.Delivery_Project.constants.Constants
import com.Delivery_Project.databinding.ActivityUserOrderDesriptionBinding
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.pojo.User

class UserOrderDescription: AppCompatActivity() {
    private lateinit var binding: ActivityUserOrderDesriptionBinding
    private val adapter = UserOrderDescriptionAdapter()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserOrderDesriptionBinding.inflate(layoutInflater)
        val dishList = intent.getSerializableExtra("Dishes") as ArrayList<Dish>
        val phoneNumber = intent.getSerializableExtra("PhoneNumber").toString()
        val address = intent.getSerializableExtra("Address") as String
        val customer = intent.getSerializableExtra("Customer") as User
        val totalCost = intent.getSerializableExtra("TotalCost") as Double
        setContentView(R.layout.activity_user_order_desription)
        binding.recyclerviewUserOrderDescription.adapter = adapter
        setContentView(binding.root)
        binding.phoneOrderDescription.text = "Phone number: $phoneNumber"
        binding.addressOrderDescription.text = "Address: $address"
        binding.customerOrderDescription.text = "Customer: ${customer.FullName}"
        binding.costOrderDescription.text = "Cost: $totalCost${Constants.SharedPreferences.COST_DOLLAR}"
        adapter.setItems(dishList)
    }
}