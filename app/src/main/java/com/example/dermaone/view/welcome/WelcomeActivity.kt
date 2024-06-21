package com.example.dermaone.view.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermaone.databinding.ActivityWelcomeBinding
import com.example.dermaone.view.login.LoginActivity
import com.example.dermaone.view.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getStartedBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

//        binding.loginBtn.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//        }
//        binding.registerBtn.setOnClickListener {
//            startActivity(Intent(this, RegisterActivity::class.java))
//        }
    }
}