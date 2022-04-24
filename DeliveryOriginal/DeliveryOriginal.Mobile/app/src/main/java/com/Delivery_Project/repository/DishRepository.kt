package com.Delivery_Project.repository

import com.Delivery_Project.retrofit.InterfaceAPI

class DishRepository constructor(private val interfaceAPI: InterfaceAPI) {
    fun  getDish() = interfaceAPI.getDish()
}