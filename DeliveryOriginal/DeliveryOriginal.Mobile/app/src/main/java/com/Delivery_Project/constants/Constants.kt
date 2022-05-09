package com.Delivery_Project.constants

class Constants {
    class SharedPreferences{
        companion object{
            const val FILE_NAME : String = "currentUser"
            const val FIRST_STATUS: Int = 0
            const val PERMISSION_ID: Int = 2
            const val TAKEN_COOK_ORDERS_TAG : String  = "TakenCookOrdersFragment"
            const val AVAILABLE_COOK_ORDERS_TAG : String  = "AvailableCookOrdersFragment"
            const val TAKEN_DELIVERY_ORDERS_TAG : String  = "TakenDeliveryOrdersFragment"
            const val HOME_TAG = "HomeFragment"
            const val DATABASE_VERSION: Int = 3
            const val DATABASE_NAME : String  = "orders.db"
            const val CART : String  = "cart"
            const val DISH_ID : String  = "dishId"
            const val ID : String  = "id"
            const val NAME : String  = "name"
            const val IMAGE : String  = "imageUrl"
            const val DESCRIPTION : String  = "description"
            const val CATEGORY_ID : String  = "categoryId"
            const val COST : String  = "cost"
            const val CATEGORY : String  = "category"
            const val DISH : String  = "dish"
            const val LOGIN_REQUIRED_REGISTRATION : String = "Login must be 4 characters long!"
            const val PASSWORD_REQUIRED_REGISTRATION : String = "Password must be 8 characters long!"
            const val FULL_NAME_REQUIRED_REGISTRATION : String = "Full name required!"
            const val PASSWORD_REPEAT_MATCHES_REGISTRATION : String = "Password does not match!"
            const val LOGIN_REQUIRED : String = "Login required!"
            const val PASSWORD_REQUIRED : String = "Password required!"
            const val CITY_REQUIRED : String = "City required!"
            const val APARTMENT_REQUIRED : String = "Apartment required!"
            const val STREET_REQUIRED : String = "Street required!"
            const val HOUSE_REQUIRED : String = "House required!"
            const val PHONE_NUMBER_REQUIRED : String = "Phone number required!"
            const val COST_DOLLAR = " $"
        }
    }
}