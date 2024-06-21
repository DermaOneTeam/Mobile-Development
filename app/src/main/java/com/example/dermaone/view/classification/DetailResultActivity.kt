package com.example.dermaone.view.classification

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermaone.R
import com.example.dermaone.api.responses.PredictResponse
import com.example.dermaone.databinding.ActivityDetailResultBinding
import com.example.dermaone.view.main.MainActivity

class DetailResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailResultBinding
    private var currentImageUri: Uri? = null
    private var predictResponseList: PredictResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        predictResponseList = intent.getParcelableExtra("predictResponse")
        currentImageUri = intent.getParcelableExtra("predictImage")
        binding.aboutDisease.text = predictResponseList!!.tentang
        binding.preventionMethods.text = predictResponseList!!.pencegahan
        binding.drugAdvice.text = predictResponseList!!.obat

        setAction()
    }
    private fun setAction(){
        binding.arrowDownBtn.setOnClickListener {
            val intent = Intent(this, ClassificationActivity::class.java)
            intent.putExtra("predictResponse", predictResponseList)
            intent.putExtra("predictImage", currentImageUri)
            startActivity(intent)
            overridePendingTransition(R.anim.swipe_up_top, R.anim.swipe_down_bottom)
        }
        binding.backToHomeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}