package com.example.dermaone.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.dermaone.R
import com.example.dermaone.databinding.ActivityMainBinding
import com.example.dermaone.view.fragment.article.ArticleFragment
import com.example.dermaone.view.fragment.home.HomeFragment
import com.example.dermaone.view.fragment.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_home -> replaceFragment(HomeFragment())
                R.id.item_article -> replaceFragment(ArticleFragment())
                R.id.item_profile -> replaceFragment(ProfileFragment())
                else -> {
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragment.setMenuVisibility(true)
        fragmentTransition.replace(R.id.frame_layout, fragment)
        fragmentTransition.commit()
    }
}