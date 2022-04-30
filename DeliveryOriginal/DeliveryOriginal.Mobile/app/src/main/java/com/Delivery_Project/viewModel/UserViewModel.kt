package com.Delivery_Project.viewModel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Delivery_Project.pojo.User
import com.Delivery_Project.pojo.UserRole
import com.Delivery_Project.repository.UserRepository
import com.Delivery_Project.ui.ui.activity.CookActivity
import com.Delivery_Project.ui.ui.activity.DeliveryActivity
import com.Delivery_Project.ui.ui.activity.MainActivity
import com.Delivery_Project.utility.RegistrationUtility
import com.Delivery_Project.utility.SharedPreferencesUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserViewModel constructor(private val repository: UserRepository): ViewModel(){

    fun getUser(login: String, password: String, context: Context){
        val errorMessage = MutableLiveData<String>()
        var userList: List<User> ? = null
        val response = repository.getUser()
        response.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful)  {
                    userList = response.body()
                    getActivity(login,password,userList!!,context)
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
    fun checkUser(login: String, password: String, full_name: String, context: Context){
        val errorMessage = MutableLiveData<String>()
        var userList: List<User> ? = null
        val response = repository.getUser()
        response.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful)  {
                    userList = response.body()

                    if(isUserExists(userList,login)!!){
                        Toast.makeText(context, "User already exists!", Toast.LENGTH_LONG).show()
                    } else {
                        RegistrationUtility.requestRegistration(login,password,full_name, context)
                    }
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun isUserExists(userList:List<User>?, login: String):Boolean?{
        return userList?.any{it.Login == login}
    }
    fun validateUser(login: String, password: String, list: List<User>, context: Context): User? {
        var newUser: User ?= null
        if(login.isEmpty() || password.isEmpty()){
            Toast.makeText(context, "Fill in all fields!" , Toast.LENGTH_LONG).show()
        }else{
            newUser = list!!.firstOrNull { p -> p.Login == login && p.Password == password }
        }
        return newUser
    }

    fun getActivity(login: String, password: String,list: List<User>, context: Context){
        val user = validateUser(login, password, list, context)
        if(user!=null){

            SharedPreferencesUtility.saveUser(user,context)


            when(UserRole.fromInt(user.Role)){
                UserRole.Regulars -> {
                    val regularIntent = Intent(context, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    regularIntent.putExtra("User",user)
                    startActivity(context,regularIntent, null)
                }
                UserRole.Cooks->{
                    val cookIntent = Intent(context, CookActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    cookIntent.putExtra("User",user)
                    ContextCompat.startActivity(context, cookIntent, null)
                }
                UserRole.Deliveries->{
                    val deliveryIntent = Intent(context, DeliveryActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    deliveryIntent.putExtra("User",user)
                    ContextCompat.startActivity(context, deliveryIntent, null)
                }

                else -> throw IllegalStateException()
            }
        } else {
            Toast.makeText(context, "User is not found!", Toast.LENGTH_LONG).show()
        }
    }
}