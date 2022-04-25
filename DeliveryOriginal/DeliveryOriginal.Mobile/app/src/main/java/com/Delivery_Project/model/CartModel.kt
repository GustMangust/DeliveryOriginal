package com.Delivery_Project.model

import com.Delivery_Project.pojo.Category

data class CartModel(
    var dishId: Int,
    var name: String,
    var image :String,
    var description :String,
    var category : Category?,
    var cost: Double

)
