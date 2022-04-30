package com.Delivery_Project.repository

import com.Delivery_Project.retrofit.InterfaceAPI

class OrderRepository constructor(private val interfaceAPI: InterfaceAPI) {
    fun  getOrder() = interfaceAPI.getOrder()
}