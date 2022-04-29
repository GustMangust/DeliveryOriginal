package com.Delivery_Project.pojo

data class Order(
    val Address: String,
    val CurrentEmployee: Any,
    val Customer: User,
    val Dishes: List<Dish>,
    val Id: Int,
    val PhoneNumber: String,
    val Status: Int,
    val SubmittedAt: String
)