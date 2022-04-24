package com.Delivery_Project.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.repository.DishRepository
import com.Delivery_Project.repository.UserRepository
import com.Delivery_Project.viewModel.DishViewModel
import com.Delivery_Project.viewModel.UserViewModel

class UserViewModelFactory constructor(private val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            UserViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}