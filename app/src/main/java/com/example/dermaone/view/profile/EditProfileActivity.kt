package com.example.dermaone.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.dermaone.api.ApiConfig
import com.example.dermaone.api.responses.ErrorResponse
import com.example.dermaone.data.UserStatus
import com.example.dermaone.databinding.ActivityEditProfileBinding
import com.example.dermaone.view.ViewModelFactory
import com.example.dermaone.view.login.LoginViewModel
import com.example.dermaone.view.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var userStatus: UserStatus
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }
        mainViewModel.setLoading(false)
        setAction()
    }
    private fun updateUser(){
        val email = binding.etEmail.text
        val username = binding.etUsername.text
        println("email ${email} username: ${username}")
        mainViewModel.getSession().observe(this) { user ->
            userStatus = UserStatus(email.toString(), username.toString(), user.password, user.id, user.token, user.isLogin)
            if (email != null && isValidEmail(email.toString())) {
                if (username != null) {
                    try {
                        mainViewModel.setLoading(true)
                        ApiConfig.getApiService(user.token).updateUser(user.id.toInt()).enqueue(object : Callback<ErrorResponse> {
                            override fun onResponse(call: Call<ErrorResponse>, response: Response<ErrorResponse>) {
                                if (response.isSuccessful) {
                                    val userResponse = response.body()
                                    if (userResponse!!.error != true) {
                                        mainViewModel.setLoading(false)
                                        loginViewModel.saveSession(userStatus)
                                        makeToast(userResponse.message.toString())
                                        startActivity(Intent(this@EditProfileActivity, ProfileActivity::class.java))
                                    }
                                } else {
                                    mainViewModel.setLoading(false)
                                    makeToast("Unsuccessfull!")
                                }
                            }
                            override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                                mainViewModel.setLoading(false)
                                makeToast("onFailure: ${t.message}")
                            }
                        })
                    }catch (e: Exception){
                        makeToast(e.message.toString())
                    }
                } else {
                    makeToast("Username is empty")
                }
            } else {
                makeToast("Email is not valid!")
            }
        }
    }
    private fun setAction(){
        binding.ivArrowBack.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.tvSave.setOnClickListener {
            updateUser()
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun makeToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}