package com.Delivery_Project.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.repository.DishRepository
import com.Delivery_Project.repository.OrderRepository
import com.Delivery_Project.viewModel.DishViewModel
import com.Delivery_Project.viewModel.OrderViewModel

class OrderViewModelFactory constructor(private val repository: OrderRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            OrderViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}