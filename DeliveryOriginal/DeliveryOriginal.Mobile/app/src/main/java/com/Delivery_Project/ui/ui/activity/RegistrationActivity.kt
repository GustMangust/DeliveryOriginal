package com.Delivery_Project.ui.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.Delivery_Project.R
import com.Delivery_Project.databinding.ActivityRegistrationBinding
import com.Delivery_Project.pojo.DefaultResponse
import com.Delivery_Project.retrofit.InterfaceAPI
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import javax.security.auth.callback.Callback

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var loginButton = binding.signInRegistration.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        var registrationButton = binding.registerBtn.setOnClickListener {
            val login_element = findViewById<EditText>(R.id.login_registration)
            val full_name_element = findViewById<EditText>(R.id.full_name)
            val password_element = findViewById<EditText>(R.id.password)
            val repeat_password_element = findViewById<EditText>(R.id.repeat_password)
            val login = binding.loginRegistration.text.toString().trim()
            val full_name = binding.fullName.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val repeat_password = binding.repeatPassword.text.toString().trim()
            val role: Int = 3
            if (full_name.isEmpty()) {
                full_name_element.error = "Full name required!"
                full_name_element.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                password_element.error = "Password required!"
                password_element.requestFocus()
                return@setOnClickListener
            }
            if (repeat_password.isEmpty()) {
                repeat_password_element.error = "Password required!"
                repeat_password_element.requestFocus()
                return@setOnClickListener
            }

            requestRegistration(login, password, full_name, role)
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


        private fun requestRegistration(login: String, password: String, full_name: String, role: Int){
        val jsonObject = JSONObject()
        jsonObject.put("Login", login)
        jsonObject.put("Password", password)
        jsonObject.put("FullName", full_name)
        jsonObject.put("Role", role)

        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = InterfaceAPI.getInstance().addUser(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )

                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
}


