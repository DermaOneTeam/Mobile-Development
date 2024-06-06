package com.example.dermaone.view.classification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermaone.R
import com.example.dermaone.databinding.ActivityClassificationBinding

class ClassificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}