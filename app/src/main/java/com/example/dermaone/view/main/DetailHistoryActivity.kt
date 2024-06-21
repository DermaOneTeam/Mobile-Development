package com.example.dermaone.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.dermaone.api.ApiConfig
import com.example.dermaone.api.responses.ErrorResponse
import com.example.dermaone.api.responses.HistoriesResponse
import com.example.dermaone.databinding.ActivityDetailHistoryBinding
import com.example.dermaone.view.ViewModelFactory
import com.example.dermaone.view.login.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding
    private var selectedHistory: HistoriesResponse? = null
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedHistory = intent.getParcelableExtra("listHistory")
        Glide.with(this)
            .load(selectedHistory!!.image)
            .into(binding.ivDiseaseImage)
        binding.aboutDisease.text = selectedHistory!!.tentang
        binding.preventionMethods.text = selectedHistory!!.pencegahan
        binding.drugAdvice.text = selectedHistory!!.obat

        println("history id: ${selectedHistory!!.id}")

        binding.ivDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Alert!")
            builder.setMessage("Are you sure want to delete this case?")
            builder.setCancelable(false)
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
            builder.setPositiveButton(android.R.string.yes) { _, _ ->
                deleteHistory()
                finish()
            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->
            }
            val alertDialogBox = builder.create()
            alertDialogBox.show()
        }
        binding.ivArrowBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun deleteHistory(){
        mainViewModel.getSession().observe(this){ user ->
            try {
                ApiConfig.getApiService(user.token).deleteHistory(selectedHistory!!.id!!.toInt()).enqueue(object : Callback<ErrorResponse> {
                    override fun onResponse(call: Call<ErrorResponse>, response: Response<ErrorResponse>) {
                        if(response.isSuccessful){
                            startActivity(Intent(this@DetailHistoryActivity, MainActivity::class.java)).also {
                                makeToast("Case deleted!")
                            }
                        }else{
                            makeToast("Unsuccessful: ${response.message()}")
                            println("Unsuccessful: ${response.message()}")
                            println("Unsuccessful: ${response.body()}")
                            println("Unsuccessful: ${response.code()}")
                        }
                    }
                    override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                        makeToast("onFailure: ${t.message}")
                        println("onFailure: ${t.message}")
                    }
                })
            }catch (e: Exception){
                makeToast(e.message.toString())
                println("exception: ${e.message}")
            }
        }
    }
    private fun makeToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}