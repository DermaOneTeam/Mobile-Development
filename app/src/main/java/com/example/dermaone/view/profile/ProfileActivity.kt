package com.example.dermaone.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermaone.R
import com.example.dermaone.databinding.ActivityProfileBinding
import com.example.dermaone.view.main.MainActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}