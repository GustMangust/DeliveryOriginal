package com.Delivery_Project.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.Exception

data class User (
    @SerializedName("Id")
    @Expose
    var Id: Int,
    @SerializedName("Login")
    @Expose
    var Login: String,
    @SerializedName("Password")
    @Expose
    var Password: String,
    @SerializedName("FullName")
    @Expose
    var FullName: String,
    @SerializedName("Role")
    @Expose
    var Role: Int
    ):Serializable

enum class UserRole(val Role: Int){
    Regulars(3),
    Cooks(4),
    Deliverymens(5);

    companion object{
        fun fromInt(givenInt:Int):UserRole{
          return when (givenInt){
              Regulars.Role -> Regulars
              Cooks.Role -> Cooks
              Deliverymens.Role -> Deliverymens
              else -> throw Exception("Invalid role $givenInt , available ids are ${values().map{it.Role}}")
          }
        }
    }
}
