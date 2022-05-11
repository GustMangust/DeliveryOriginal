package com.Delivery_Project.ui.ui.activity

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.Delivery_Project.R
import com.Delivery_Project.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var welcomeLogin = binding.loginWelcome.setOnClickListener {
            startActivity( Intent(this, LoginActivity::class.java) );
        }
    }

    fun register(view: android.view.View) {
        startActivity( Intent(this, RegistrationActivity::class.java) );
    }
}