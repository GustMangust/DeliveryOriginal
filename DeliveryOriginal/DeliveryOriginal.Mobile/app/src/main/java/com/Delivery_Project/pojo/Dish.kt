package com.Delivery_Project.pojo

import java.io.Serializable

data class Dish(
    val Category: Category,
    val Cost: Double,
    val Description: String,
    val Id: Int,
    val ImageUrl: String,
    val Name: String
):Serializable