package com.example.dermaone.view.profile.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.dermaone.R
import com.example.dermaone.api.ApiConfig
import com.example.dermaone.api.responses.ErrorResponse
import com.example.dermaone.databinding.ActivitySettingsBinding
import com.example.dermaone.view.ViewModelFactory
import com.example.dermaone.view.classification.LoadingDialog
import com.example.dermaone.view.main.MainActivity
import com.example.dermaone.view.main.MainViewModel
import com.example.dermaone.view.profile.ProfileActivity
import com.example.dermaone.view.register.RegisterViewModel
import com.example.dermaone.view.welcome.WelcomeActivity
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var darkModeSwitch: Switch
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        enableEdgeToEdge()
//        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        darkModeSwitch = binding.switchDarkMode

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        settingsViewModel.isDarkMode.observe(this) { isEnabled ->
            darkModeSwitch.isChecked = isEnabled
        }

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.toggleDarkMode(isChecked)
        }

        setAction()
    }
    private fun deleteUser(){
        var loadingDialog = LoadingDialog(this)
        mainViewModel.getSession().observe(this) { user ->
            try {
                loadingDialog.startLoadingDialog()
                ApiConfig.getApiService(user.token).deleteUser(user.id.toInt()).enqueue(object : retrofit2.Callback<ErrorResponse> {
                    override fun onResponse(call: Call<ErrorResponse>, response: Response<ErrorResponse>) {
                        if (response.isSuccessful) {
                            val deleteResponse = response.body()
                            if (deleteResponse!!.error != true) {
                                loadingDialog.dismissDialog()
                                makeToast("User Deleted!")
                                startActivity(Intent(this@SettingsActivity, WelcomeActivity::class.java))
                            } else {
                                loadingDialog.dismissDialog()
                                makeToast("error: ${deleteResponse.message}")
                                println("error: ${deleteResponse.message}")
                            }
                        } else {
                            loadingDialog.dismissDialog()
                            println("unSuccessfull ${response.message()}")
                            makeToast("unSuccessfull ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                        loadingDialog.dismissDialog()
                        println("onFailure: ${t.message}")
                        makeToast("onFailure: ${t.message}")
                    }
                })
            } catch (e: Exception) {
                makeToast("Exception: ${e.message}")
                println("Exception: ${e.message}")
            }
        }
    }
    private fun setAction(){
        binding.ivArrowBack.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.logoutBtn.setOnClickListener {
            mainViewModel.logout()

            startActivity(Intent(this, WelcomeActivity::class.java))
        }
        binding.linearLayoutDeleteAccount.setOnClickListener {
            deleteUser()
        }
    }
    private fun makeToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}