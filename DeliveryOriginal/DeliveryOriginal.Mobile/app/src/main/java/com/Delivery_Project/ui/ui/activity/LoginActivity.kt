package com.Delivery_Project.ui.ui.activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.R
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.LOGIN_REQUIRED
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.PASSWORD_REQUIRED
import com.Delivery_Project.databinding.ActivityLoginBinding
import com.Delivery_Project.factory.OrderViewModelFactory
import com.Delivery_Project.factory.UserViewModelFactory
import com.Delivery_Project.pojo.UserRole
import com.Delivery_Project.repository.OrderRepository
import com.Delivery_Project.repository.UserRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.utility.EncryptionUtility
import com.Delivery_Project.utility.SharedPreferencesUtility
import com.Delivery_Project.viewModel.OrderViewModel
import com.Delivery_Project.viewModel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: UserViewModel
    private val retrofitService = InterfaceAPI.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, UserViewModelFactory(
            UserRepository(InterfaceAPI.interfaceAPI!!)
        )).get(UserViewModel::class.java)
        viewModel.getLoggedUser(this)
        var button = findViewById<Button>(R.id.signBtn)
            button.setOnClickListener {
                val login =  binding.loginLogin.text.toString().trim()
                val password =  binding.passwordLogin.text.toString().trim()
                val login_element = findViewById<EditText>(R.id.login_login)
                val password_element = findViewById<EditText>(R.id.password_login)
                if (login.isEmpty()) {
                    login_element.error = LOGIN_REQUIRED
                    login_element.requestFocus()
                    return@setOnClickListener
                }
                if (password.isEmpty()) {
                    password_element.error = PASSWORD_REQUIRED
                    password_element.requestFocus()
                    return@setOnClickListener
                }
                var encryptedPassword = EncryptionUtility.encryptString(password)
                viewModel.getUser(login,encryptedPassword,applicationContext)
            }
    }

    fun register(view: android.view.View) {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }
}