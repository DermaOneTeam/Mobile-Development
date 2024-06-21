package com.example.dermaone.view.classification

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.example.dermaone.R
import com.example.dermaone.api.responses.PredictResponse
import com.example.dermaone.databinding.ActivityClassificationBinding
import com.example.dermaone.view.main.MainActivity

class ClassificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassificationBinding
//    private var byteArray: ByteArray? = null
    private var currentImageUri: Uri? = null
    private var predictResponseList: PredictResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        predictResponseList = intent.getParcelableExtra("predictResponse")
        currentImageUri = intent.getParcelableExtra("predictImage")
//        currentImageUri = intent.getParcelableExtra(MainActivity.EXTRA_IMAGE_URI)
        if(currentImageUri != null){
            binding.ivImageClassification.setImageURI(currentImageUri)
        }
        binding.tvDescriptionClassification.text = predictResponseList!!.tentang
        setAction()
    }
    private fun setAction(){
        binding.swipeToDetail.setOnClickListener {
            val intent = Intent(this, DetailResultActivity::class.java)
            intent.putExtra("predictResponse", predictResponseList)
            intent.putExtra("predictImage", currentImageUri)
            startActivity(intent)
            overridePendingTransition(R.anim.swipe_up_bottom, R.anim.swipe_down_top)
        }
    }
}