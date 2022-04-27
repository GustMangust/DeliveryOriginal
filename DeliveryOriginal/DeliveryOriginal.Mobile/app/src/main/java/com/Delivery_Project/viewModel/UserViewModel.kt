package com.Delivery_Project.viewModel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.ParcelFileDescriptor.MODE_WORLD_WRITEABLE
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Delivery_Project.pojo.User
import com.Delivery_Project.pojo.UserRole
import com.Delivery_Project.repository.UserRepository
import com.Delivery_Project.ui.ui.activity.MainActivity
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
    fun validateUser(login: String, password: String, list: List<User>, context: Context): User {
        var newUser: User ?= null
        if(login.isEmpty() || password.isEmpty()){
            Toast.makeText(context, "Fill in all fields!" , Toast.LENGTH_LONG)
        }else{
            var user = list!!.firstOrNull { p -> p.Login == login && p.Password == password }
            newUser = user
        }
        return newUser!!
    }

    fun getActivity(login: String, password: String,list: List<User>, context: Context){
        var user = validateUser(login, password, list, context)
        val regularIntent = Intent(context, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        regularIntent.putExtra("User",user)
        when(UserRole.fromInt(user.Role)){
            UserRole.Regulars -> {
                //val mPrefs: SharedPreferences = context.getSharedPreferences(getSizeInt().toString(),Context.MODE_WORLD_WRITEABLE)
                startActivity(context,regularIntent, null)
            }

            else -> throw IllegalStateException()
        }
    }



}