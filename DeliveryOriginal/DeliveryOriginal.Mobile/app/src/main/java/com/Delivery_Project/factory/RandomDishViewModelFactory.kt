package com.Delivery_Project.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.repository.MenuRepository
import com.Delivery_Project.repository.RandomDishRepository
import com.Delivery_Project.viewModel.RandomDishViewModel

class RandomDishViewModelFactory constructor(private val repository: RandomDishRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RandomDishViewModel::class.java)) {
            RandomDishViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}