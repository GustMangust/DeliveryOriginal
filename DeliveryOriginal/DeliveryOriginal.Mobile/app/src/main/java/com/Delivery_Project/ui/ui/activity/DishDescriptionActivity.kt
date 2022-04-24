package com.Delivery_Project.ui.ui.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.Delivery_Project.R
import com.Delivery_Project.database.OrderHelper
import com.Delivery_Project.databinding.ActivityDishDescriptionBinding
import com.Delivery_Project.model.OrderModel
import com.Delivery_Project.pojo.Dish
import com.bumptech.glide.Glide

class DishDescriptionActivity : AppCompatActivity() {
            private lateinit var orderHelper: OrderHelper
            private lateinit var binding: ActivityDishDescriptionBinding
            override fun onCreate(savedInstanceState: Bundle?) {
                val dish  = intent.getSerializableExtra("Dish") as Dish
                super.onCreate(savedInstanceState)
                binding = ActivityDishDescriptionBinding.inflate(layoutInflater)
                setContentView(binding.root)
                binding.dishDescriptionCost.text = "Cost: " +dish.Cost.toString() + " $"
                binding.dishDescriptionIngredients.text = dish.Description
                binding.dishDescriptionName.text = dish.Name
                Glide.with(baseContext).load(dish.ImageUrl).into(binding.dishDescriptionImg)

                orderHelper = OrderHelper(this)
                binding.addToCart.setOnClickListener{ addOrder()}
    }

    private fun addOrder(){
        val dish = intent.getSerializableExtra("Dish") as Dish
        val name  = dish.Name
        val image = dish.ImageUrl
        val description = dish.Description
        val amount = 1;
        val cost = dish.Cost

        val order = OrderModel(name = name, image = image, description = description, amount = amount, cost = cost)
        val status = orderHelper.insertOrder(order)

        if(status > -1){
            Toast.makeText(this, "Order added!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Order not added!", Toast.LENGTH_SHORT).show()
        }
    }
}