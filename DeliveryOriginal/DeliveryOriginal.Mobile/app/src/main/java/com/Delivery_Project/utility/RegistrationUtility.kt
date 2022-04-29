package com.Delivery_Project.utility

import android.content.Context
import android.content.Intent
import android.util.Log
import com.Delivery_Project.pojo.UserRole
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.ui.ui.activity.LoginActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class RegistrationUtility {
    companion object{
        fun requestRegistration(login: String, password: String, full_name: String,context: Context){
            val jsonObject = JSONObject()
            val encryptedPassword = EncryptionUtility.encryptString(password);

            jsonObject.put("Login", login)
            jsonObject.put("Password", encryptedPassword)
            jsonObject.put("FullName", full_name)
            jsonObject.put("Role", UserRole.Regulars.Role)

            val jsonObjectString = jsonObject.toString()

            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            CoroutineScope(Dispatchers.IO).launch {
                val response = InterfaceAPI.getInstance().addUser(requestBody)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {

                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(
                            JsonParser.parseString(
                                response.body()
                                    ?.string()
                            )
                        )

                        Log.d("Pretty Printed JSON :", prettyJson)
                        context.startActivity(Intent(context,LoginActivity::class.java))
                    } else {
                        Log.e("RETROFIT_ERROR", response.code().toString())
                    }
                }
            }

        }
    }
}
