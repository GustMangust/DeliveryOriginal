package com.Delivery_Project.repository

import com.Delivery_Project.retrofit.InterfaceAPI

class MenuRepository constructor(private val interfaceAPI: InterfaceAPI) {
    fun  getCategory() = interfaceAPI.getCategory()
}