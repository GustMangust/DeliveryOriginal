package com.Delivery_Project.pojo

import java.io.Serializable

data class Order(
    val Address: String,
    val CurrentEmployee: User,
    val Customer: User,
    val Dishes: ArrayList<Dish>,
    val Id: Int,
    val PhoneNumber: String,
    val Status: Int,
    val SubmittedAt: String
):Serializable