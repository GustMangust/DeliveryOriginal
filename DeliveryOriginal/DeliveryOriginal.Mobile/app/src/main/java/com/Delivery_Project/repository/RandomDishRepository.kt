package com.Delivery_Project.repository

import com.Delivery_Project.retrofit.InterfaceAPI

class RandomDishRepository constructor(private val interfaceAPI: InterfaceAPI) {
    fun  getRandomDish() = interfaceAPI.getRandomDish()
}