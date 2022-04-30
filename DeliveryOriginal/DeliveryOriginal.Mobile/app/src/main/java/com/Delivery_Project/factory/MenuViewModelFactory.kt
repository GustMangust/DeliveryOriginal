package com.Delivery_Project.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.repository.MenuRepository
import com.Delivery_Project.viewModel.MenuViewModel

class MenuViewModelFactory constructor(private val repository: MenuRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            MenuViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}