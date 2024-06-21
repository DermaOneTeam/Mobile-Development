package com.example.dermaone.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.dermaone.R
import com.example.dermaone.api.ApiConfig
import com.example.dermaone.api.responses.ErrorResponse
import com.example.dermaone.databinding.ActivityAboutUsBinding
import com.example.dermaone.databinding.ActivityChangePasswordBinding
import com.example.dermaone.view.ViewModelFactory
import com.example.dermaone.view.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }
        mainViewModel.setLoading(false)
        setAction()
    }
    private fun changePassword(){
        val currentPassword = binding.etCurrentPassword.text
        val newPassword = binding.etNewPassword.text
        val confirmNewPassword = binding.etConfirmNewPassword.text
        mainViewModel.getSession().observe(this){ user ->
            binding.tvErrorMessage.text = ""
            if(currentPassword.equals(user.password)){
                if(newPassword.length > 7){
                    if(newPassword.equals(confirmNewPassword)){
                        try {
                            mainViewModel.setLoading(true)
                            ApiConfig.getApiService(user.token).updatePassword(user.id.toInt()).enqueue(object : Callback<ErrorResponse>{
                                override fun onResponse(call: Call<ErrorResponse>, response: Response<ErrorResponse>) {
                                    if(response.isSuccessful) {
                                        mainViewModel.setLoading(false)
                                        startActivity(Intent(this@ChangePasswordActivity, ProfileActivity::class.java)).also {
                                            makeToast("Password Changed!")
                                        }
                                    }else{
                                        mainViewModel.setLoading(false)
                                        makeToast("Unsuccessful: ${response.message()}")
                                        println("Unsuccessful: ${response.message()}")
                                    }
                                }
                                override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                                    mainViewModel.setLoading(false)
                                    makeToast("onFailure: ${t.message}")
                                    println("onFailure: ${t.message}")
                                }
                            })
                        }catch (e: Exception){
                            makeToast("exception: ${e.message}")
                            println("exception: ${e.message}")
                        }
                    }else{
                        binding.tvErrorMessage.text = "New password is not matching!"
                    }
                }else{
                    binding.tvErrorMessage.text = "Password is 8 character minimum!"
                }
            }else{
                binding.tvErrorMessage.text = "Current password is incorrect!"
            }
        }
    }
    private fun setAction(){
        binding.ivArrowBack.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.changeButton.setOnClickListener {
            changePassword()
        }
    }
    private fun makeToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}