package com.example.dermaone.view.splashScreen

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.dermaone.databinding.ActivitySplashScreenBinding
import com.example.dermaone.view.ViewModelFactory
import com.example.dermaone.view.main.MainViewModel
import com.example.dermaone.view.welcome.WelcomeActivity
import androidx.activity.viewModels
import com.example.dermaone.view.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setThemeFromPreferences()

        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                binding.ivLogo.alpha = 0f
                binding.tvLogo.alpha = 0f
                binding.tvLogo.animate().setDuration(5000).alpha(1f).also {
                    binding.ivLogo.animate().setDuration(3000).alpha(1f)
                } .withEndAction{
                    val intent = Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
            }else{
                binding.ivLogo.alpha = 0f
                binding.tvLogo.alpha = 0f
                binding.tvLogo.animate().setDuration(5000).alpha(1f).also {
                    binding.ivLogo.animate().setDuration(3000).alpha(1f)
                } .withEndAction{
                    val intent = Intent(this, MainActivity::class.java)
                    println(user.id)
                    intent.putExtra("user_id", user.id)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
            }
        }
    }
    private fun setThemeFromPreferences() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}