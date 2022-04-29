package com.Delivery_Project.ui.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.Delivery_Project.R
import com.Delivery_Project.adapter.CookOrderDescriptionAdapter
import com.Delivery_Project.adapter.DishAdapter
import com.Delivery_Project.databinding.ActivityCookOrderDesriptionBinding
import com.Delivery_Project.pojo.Dish

class CookOrderDescription: AppCompatActivity() {
    private lateinit var binding: ActivityCookOrderDesriptionBinding
    private val adapter = CookOrderDescriptionAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCookOrderDesriptionBinding.inflate(layoutInflater)
        val dishList = intent.getSerializableExtra("Dishes") as ArrayList<Dish>
        setContentView(R.layout.activity_cook_order_desription)
        binding.recyclerviewCookOrderDescription.adapter = adapter
        setContentView(binding.root)
        adapter.setItems(dishList)

    }
}