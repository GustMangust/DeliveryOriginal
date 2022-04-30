package com.Delivery_Project.repository

import com.Delivery_Project.retrofit.InterfaceAPI

class UserRepository constructor(private val interfaceAPI: InterfaceAPI) {
    fun  getUser() = interfaceAPI.getUser()
    fun checkUser() = interfaceAPI.getUser()
}