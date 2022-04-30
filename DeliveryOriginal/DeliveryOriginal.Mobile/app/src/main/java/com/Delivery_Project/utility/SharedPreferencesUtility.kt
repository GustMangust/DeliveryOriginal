package com.Delivery_Project.utility

import android.content.Context
import android.content.SharedPreferences
import com.Delivery_Project.constants.Constants
import com.Delivery_Project.pojo.User
import com.google.gson.Gson
import org.json.JSONObject

class SharedPreferencesUtility(){
    companion object{
        fun saveUser(user:User, context: Context){
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.SharedPreferences.fileName,Context.MODE_PRIVATE)

            val editor:SharedPreferences.Editor =  sharedPreferences.edit()

            editor.putString("User", convertUserToJson(user))

            editor.apply()
        }

        fun getUser(context: Context) : User?{
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.SharedPreferences.fileName,Context.MODE_PRIVATE)

            val gson = Gson()

            val userString = sharedPreferences.getString("User", null.toString())

            val user = if(userString == "null") null else gson.fromJson(userString,User::class.java)

            return user
        }

        fun deleteUser(context: Context){
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.SharedPreferences.fileName,Context.MODE_PRIVATE)

            val editor:SharedPreferences.Editor =  sharedPreferences.edit()

            editor.clear()

            editor.apply()
        }

        private fun convertUserToJson(user: User):String{
            return Gson().toJson(user)
        }
    }
}