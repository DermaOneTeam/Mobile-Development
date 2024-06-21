package com.example.dermaone.view.fragment.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermaone.R
import com.example.dermaone.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}