package com.Delivery_Project.ui.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.R
import com.Delivery_Project.constants.Constants
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.FULL_NAME_REQUIRED_REGISTRATION
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.LOGIN_REQUIRED_REGISTRATION
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.PASSWORD_REPEAT_MATCHES_REGISTRATION
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.PASSWORD_REQUIRED
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.PASSWORD_REQUIRED_REGISTRATION
import com.Delivery_Project.databinding.ActivityRegistrationBinding
import com.Delivery_Project.factory.UserViewModelFactory
import com.Delivery_Project.repository.UserRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.viewModel.UserViewModel

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    lateinit var viewModel: UserViewModel
    private val retrofitService = InterfaceAPI.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var loginButton = binding.signInRegistration.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        var registrationButton = binding.registerBtn.setOnClickListener {
            val loginElement = findViewById<EditText>(R.id.login_registration)
            val fullNameElement = findViewById<EditText>(R.id.full_name)
            val passwordElement = findViewById<EditText>(R.id.password)
            val repeatPasswordElement = findViewById<EditText>(R.id.repeat_password)
            val login = binding.loginRegistration.text.toString().trim()
            val fullName = binding.fullName.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val repeatPassword = binding.repeatPassword.text.toString().trim()

            if (login.isEmpty() || login.length < 4) {
                loginElement.error = LOGIN_REQUIRED_REGISTRATION
                loginElement.requestFocus()
                return@setOnClickListener
            }

            if (fullName.isEmpty()) {
                fullNameElement.error = FULL_NAME_REQUIRED_REGISTRATION
                fullNameElement.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty() || password.length < 8) {
                passwordElement.error = PASSWORD_REQUIRED_REGISTRATION
                passwordElement.requestFocus()
                return@setOnClickListener
            }

            if(repeatPassword != password){
                repeatPasswordElement.error = PASSWORD_REPEAT_MATCHES_REGISTRATION
                repeatPasswordElement.requestFocus()
                return@setOnClickListener
            }
            viewModel = ViewModelProvider(this, UserViewModelFactory(UserRepository(retrofitService))).get(
                UserViewModel::class.java)

            viewModel.checkUser(login, password, fullName, this)
        }
    }
}


