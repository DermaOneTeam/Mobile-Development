package com.example.dermaone.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.dermaone.api.ApiConfig
import com.example.dermaone.api.responses.LoginResponse
import com.example.dermaone.data.UserStatus
import com.example.dermaone.databinding.ActivityLoginBinding
import com.example.dermaone.view.ViewModelFactory
import com.example.dermaone.view.forgetPassword.ForgetPasswordActivity
import com.example.dermaone.view.main.MainActivity
import com.example.dermaone.view.main.MainViewModel
import com.example.dermaone.view.register.RegisterActivity
import com.example.dermaone.view.register.RegisterViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.isLoading.observe(this){
            showLoading(it)
        }
        loginViewModel.setLoading(false)

        setAction()
    }
    private fun login(email: String, password: String){
        try {
            loginViewModel.setLoading(true)
            ApiConfig.getApiService().login(email, password).enqueue(object: Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val loginResponse = response.body()
                    if(response.isSuccessful){
                        if(loginResponse?.error != true){
                            println(loginResponse!!.username +" "+ loginResponse!!.id +" "+ loginResponse!!.token + " " + loginResponse!!.email)
                            loginViewModel.saveSession(UserStatus(email, loginResponse!!.username.toString(), password, loginResponse?.id.toString(), loginResponse!!.token.toString(), true))
                            loginViewModel.setLoading(false)
                            makeToast(loginResponse?.message.toString())
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }else{
                            loginViewModel.setLoading(false)
                            makeToast("${loginResponse?.message}")
                        }
                    }else{
                        loginViewModel.setLoading(false)
                        makeToast("Email or password is wrong!")
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginViewModel.setLoading(false)
                    makeToast("onFailure: ${t.message}")
                }
            })
        }catch (e: Exception){
            Log.e("exception: ", e.message.toString())
        }
    }

    private fun setAction(){
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
//        binding.tvForgetPassword.setOnClickListener {
//            startActivity(Intent(this, ForgetPasswordActivity::class.java))
//        }
        binding.loginBtn.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
            val ivError = binding.ivError
            var tvError = binding.tvError
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                login(email, password)
            }else{
                ivError.isVisible = true
                tvError.text = "Email or password is empty!"
            }
        }
    }

    private fun makeToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}