package com.example.dermaone.view.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dermaone.R
import com.example.dermaone.databinding.ActivityMainBinding
import com.example.dermaone.view.fragment.article.ArticleFragment
import com.example.dermaone.view.fragment.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}

    private var clicked = false
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.menu.getItem(1).isEnabled = false
        binding.bottomNav.selectedItemId = R.id.item_home
        replaceFragment(HomeFragment())
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_home -> replaceFragment(HomeFragment())
                R.id.item_article -> replaceFragment(ArticleFragment())
                else -> {
                }
            }
            true
        }

        binding.fab.setOnClickListener {
            fabOnClick()
        }
        binding.fabCamera.setOnClickListener {
            startCamera()
        }
        binding.fabGalery.setOnClickListener {
            startGallery()
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragment.setMenuVisibility(true)
        fragmentTransition.replace(R.id.frame_layout, fragment)
        fragmentTransition.commit()
    }

    private fun fabOnClick(){
        setVisibility(clicked)
        setAnimation(clicked)
        setClicable(clicked)
        clicked = !clicked
    }
    private fun setVisibility(clicked: Boolean) {
        if (clicked){
            binding.fabCamera.visibility = View.VISIBLE
            binding.fabGalery.visibility = View.VISIBLE
        }else{
            binding.fabCamera.visibility = View.INVISIBLE
            binding.fabGalery.visibility = View.INVISIBLE
        }
    }
    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            binding.fabCamera.startAnimation(fromBottom)
            binding.fabGalery.startAnimation(fromBottom)
            binding.fab.startAnimation(rotateOpen)
        }else{
            binding.fabCamera.startAnimation(toBottom)
            binding.fabGalery.startAnimation(toBottom)
            binding.fab.startAnimation(rotateClose)
        }
    }

    private fun setClicable(clicked: Boolean){
        if(!clicked){
            binding.fabCamera.isClickable = true
            binding.fabGalery.isClickable = true
        }else{
            binding.fabCamera.isClickable = false
            binding.fabGalery.isClickable = false
        }
    }

    // galery
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
    private val launcherGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
//            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }
//    private fun showImage() {
//        currentImageUri?.let {
//            Log.d("Image URI", "showImage: $it")
////            binding.imageView.setImageURI(it)
////            binding.imageView.tag = it
//        }
//    }

    // camera
    private fun startCamera() {
//        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }
    private val launcherIntentCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            Log.d("Message", "success")
//            showImage()
        }
    }

}