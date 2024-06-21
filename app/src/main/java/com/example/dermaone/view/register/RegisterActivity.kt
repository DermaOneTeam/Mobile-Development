package com.example.dermaone.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.dermaone.api.ApiConfig
import com.example.dermaone.api.responses.ErrorResponse
import com.example.dermaone.databinding.ActivityRegisterBinding
import com.example.dermaone.view.ViewModelFactory
import com.example.dermaone.view.login.LoginActivity
import com.example.dermaone.view.main.MainActivity
import retrofit2.Call
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel.isLoading.observe(this){
            showLoading(it)
        }
        registerViewModel.setLoading(false)

        setAction()
    }
    
    private fun register(username: String, email: String, password: String){
        try {
            registerViewModel.setLoading(true)
            ApiConfig.getApiService().register(username, email, password).enqueue(object : retrofit2.Callback<ErrorResponse> {
                override fun onResponse(call: Call<ErrorResponse>, response: Response<ErrorResponse>) {
                    val registerResponse = response.body()
                    if (response.isSuccessful) {
                        if(registerResponse?.error != true){
                            registerViewModel.setLoading(false)
                            makeToast(registerResponse?.message.toString())
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        }else{
                            registerViewModel.setLoading(false)
                            makeToast("${registerResponse?.message}")
                        }
                    } else {
                        registerViewModel.setLoading(false)
                        makeToast("Email is already taken")
                    }
                }
                override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                    registerViewModel.setLoading(false)
                    makeToast("onFailure: ${t.message}")
                }
            })
        }catch (e: Exception){
            Log.e("exception: ", e.message.toString())
        }
    }

    private fun setAction(){
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.registerBtn.setOnClickListener {
            val ivError = binding.ivError
            var tvError = binding.tvError
            val username = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            if(username.isNotEmpty() && email.isNotEmpty()){
                if(isValidEmail(email)){
                    if(password.length > 7 && confirmPassword.length > 7){
                        if(password == confirmPassword){
                            register(username, email, password)
                        }else{
                            ivError.isVisible = true
                            tvError.text = "Password did not match!"
                        }
                    }else{
                        ivError.isVisible = true
                        tvError.text = "Password must be 8 character minimum!"
                    }
                }else{
                    ivError.isVisible = true
                    tvError.text = "Email is not valid!"
                }
            }else{
                ivError.isVisible = true
                tvError.text = "Username or email is empty!"
            }
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