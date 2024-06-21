package com.example.dermaone.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.dermaone.databinding.ActivityProfileBinding
import com.example.dermaone.view.ViewModelFactory
import com.example.dermaone.view.main.MainActivity
import com.example.dermaone.view.main.MainViewModel
import com.example.dermaone.view.profile.settings.SettingsActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getSession().observe(this){ user ->
            binding.tvProfileName.text = user.username
            binding.tvProfileEmail.text = user.email
        }

        setAction()
    }
    private fun setAction(){
        binding.linearLayoutAboutUs.setOnClickListener {
            startActivity(Intent(this, AboutUsActivity::class.java))
        }
        binding.linearLayoutChangePassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
        binding.linearLayoutEdit.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
        binding.linearLayoutSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.ivArrowBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}