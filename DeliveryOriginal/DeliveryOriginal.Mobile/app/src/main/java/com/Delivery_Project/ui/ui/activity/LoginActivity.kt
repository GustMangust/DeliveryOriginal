package com.Delivery_Project.ui.ui.activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.R
import com.Delivery_Project.databinding.ActivityLoginBinding
import com.Delivery_Project.factory.UserViewModelFactory
import com.Delivery_Project.pojo.UserRole
import com.Delivery_Project.repository.UserRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.utility.EncryptionUtility
import com.Delivery_Project.utility.SharedPreferencesUtility
import com.Delivery_Project.viewModel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: UserViewModel
    private val retrofitService = InterfaceAPI.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getLoggedUser()
        var button = findViewById<Button>(R.id.signBtn)
            button.setOnClickListener {
                //var login = "Raman"
                //var password = "raman123"
                val login =  binding.loginLogin.text.toString().trim()
                val password =  binding.passwordLogin.text.toString().trim()
                val login_element = findViewById<EditText>(R.id.login_login)
                val password_element = findViewById<EditText>(R.id.password_login)
                if (login.isEmpty()) {
                    login_element.error = "Login required!"
                    login_element.requestFocus()
                    return@setOnClickListener
                }
                if (password.isEmpty()) {
                    password_element.error = "Password required!"
                    password_element.requestFocus()
                    return@setOnClickListener
                }
                var encryptedPassword = EncryptionUtility.encryptString(password)
                viewModel = ViewModelProvider(this, UserViewModelFactory(UserRepository(retrofitService))).get(UserViewModel::class.java)
                viewModel.getUser(login,encryptedPassword,applicationContext)
            }
    }
    fun getLoggedUser(){
        val user = SharedPreferencesUtility.getUser(this)

        if(user != null){
            val regularIntent = Intent(this, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            regularIntent.putExtra("User",user)
            val cookIntent = Intent(this, CookActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            cookIntent.putExtra("User",user)
            SharedPreferencesUtility.saveUser(user,this)

            when(UserRole.fromInt(user.Role)){
                UserRole.Regulars -> {
                    ContextCompat.startActivity(this, regularIntent, null)
                }
                UserRole.Cooks->{
                    ContextCompat.startActivity(this, cookIntent, null)
                }

                else -> throw IllegalStateException()
            }
        }
    }
    fun register(view: android.view.View) {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }
}