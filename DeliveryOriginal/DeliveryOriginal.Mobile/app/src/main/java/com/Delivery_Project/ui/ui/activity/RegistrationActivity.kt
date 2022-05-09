package com.Delivery_Project.ui.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.R
import com.Delivery_Project.constants.Constants
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.FULL_NAME_REQUIRED
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.PASSWORD_REQUIRED
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
                full_name_element.error = FULL_NAME_REQUIRED
                full_name_element.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                password_element.error = PASSWORD_REQUIRED
                password_element.requestFocus()
                return@setOnClickListener
            }
            if (repeat_password.isEmpty()) {
                repeat_password_element.error = PASSWORD_REQUIRED
                repeat_password_element.requestFocus()
                return@setOnClickListener
            }
            viewModel = ViewModelProvider(this, UserViewModelFactory(UserRepository(retrofitService))).get(
                UserViewModel::class.java)

            viewModel.checkUser(login, password, full_name, this)
        }
    }
}


