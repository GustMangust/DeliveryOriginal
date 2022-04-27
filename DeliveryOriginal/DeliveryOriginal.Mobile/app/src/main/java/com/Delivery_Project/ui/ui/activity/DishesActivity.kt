package com.Delivery_Project.ui.ui.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.R
import com.Delivery_Project.adapter.DishAdapter
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.databinding.ActivityDishesBinding
import com.Delivery_Project.factory.DishViewModelFactory
import com.Delivery_Project.repository.DishRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.viewModel.DishViewModel

class DishesActivity : AppCompatActivity() {
    private val TAG = "DishesActivity"
    private lateinit var binding: ActivityDishesBinding
    lateinit var viewModel: DishViewModel
    lateinit var db: DatabaseHelper
    private val retrofitService = InterfaceAPI.getInstance()
    val adapter = DishAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        val categoryId = intent.getIntExtra("Category_Id", 1)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dishes)
        getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        binding = ActivityDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerviewDishes.adapter = adapter

        db = DatabaseHelper(this);
        adapter.setDishList(db.getAllDishes(),categoryId)
    }


}