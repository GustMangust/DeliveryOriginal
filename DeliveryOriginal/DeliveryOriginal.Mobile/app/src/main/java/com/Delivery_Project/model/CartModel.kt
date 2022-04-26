package com.Delivery_Project.model

import com.Delivery_Project.pojo.Category
import com.Delivery_Project.pojo.Dish

data class CartModel(
    var dishId: Int,
    var name: String,
    var image :String?,
    var description :String,
    var category : Category?,
    var cost: Double
){
    fun convertToDish(): Dish {
        return Dish(Id = dishId, Name = name, ImageUrl = image, Description = description, Category = category, Cost = cost)
    }
}
