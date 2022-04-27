package com.Delivery_Project.ui.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.databinding.ActivityDishDescriptionBinding
import com.Delivery_Project.model.CartModel
import com.Delivery_Project.pojo.Dish
import com.bumptech.glide.Glide

class DishDescriptionActivity : AppCompatActivity() {
     private lateinit var cartHelper: DatabaseHelper
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

         cartHelper = DatabaseHelper(this)
         binding.addToCart.setOnClickListener{ addOrder()}
    }

    private fun addOrder(){
        val dish = intent.getSerializableExtra("Dish") as Dish
        val dishId = dish.Id
        val name  = dish.Name
        val image: String? = dish.ImageUrl
        val description = dish.Description
        val category = dish.Category
        val cost = dish.Cost

        val order = CartModel(dishId = dishId,name = name, image = image, description = description,category = category,  cost = cost)
        val status = cartHelper.insertOrder(order)

        if(status > -1){
            Toast.makeText(this, "Order added!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Order not added!", Toast.LENGTH_SHORT).show()
        }
    }
}