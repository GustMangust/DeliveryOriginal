package com.Delivery_Project.ui.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.databinding.ActivityDishDescriptionBinding
import com.Delivery_Project.factory.OrderViewModelFactory
import com.Delivery_Project.model.CartModel
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.repository.OrderRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.viewModel.OrderViewModel
import com.bumptech.glide.Glide

class DishDescriptionActivity : AppCompatActivity() {
     private lateinit var cartHelper: DatabaseHelper
     private lateinit var binding: ActivityDishDescriptionBinding
     @SuppressLint("SetTextI18n")
     override fun onCreate(savedInstanceState: Bundle?) {
         val dish  = intent.getSerializableExtra("Dish") as Dish
         super.onCreate(savedInstanceState)
         binding = ActivityDishDescriptionBinding.inflate(layoutInflater)
         setContentView(binding.root)
         binding.dishDescriptionCost.text = "Cost: " +dish.Cost.toString() + " $"
         binding.dishDescriptionIngredients.text = dish.Description
         binding.dishDescriptionName.text = dish.Name
         Glide.with(baseContext).load(dish.ImageUrl).into(binding.dishDescriptionImg)
         val viewModel: OrderViewModel = ViewModelProvider(this, OrderViewModelFactory(
             OrderRepository(InterfaceAPI.interfaceAPI!!)
         )
         ).get(OrderViewModel::class.java)
         cartHelper = DatabaseHelper(this)
         binding.addToCart.setOnClickListener{
            viewModel.addOrder(cartHelper,this,dish)}
    }
}