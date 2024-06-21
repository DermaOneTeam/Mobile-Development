package com.example.dermaone.view.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.dermaone.BuildConfig
import com.example.dermaone.R
import com.example.dermaone.databinding.ActivityMainBinding
import com.example.dermaone.view.ViewModelFactory
import com.example.dermaone.view.classification.LoadingDialog
import com.example.dermaone.view.classification.PreClassificationActivity
import com.example.dermaone.view.classification.getImageUri
import com.example.dermaone.view.fragment.article.ArticleFragment
import com.example.dermaone.view.fragment.home.HomeFragment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }

    private var clicked = false
    private var currentImageUri: Uri? = null
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
    }
//    companion object {
//        private const val CAMERA_PERMISSION_REQUEST_CODE = 1
//        private const val MAXIMAL_SIZE = 1000000
//        const val EXTRA_IMAGE_URI = "com.example.dermaone.IMAGE_URI"
//    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getSession().observe(this) { user ->
            println(user.id)
            binding.bottomNav.menu.getItem(1).isEnabled = false
            binding.bottomNav.selectedItemId = R.id.item_home
            val homeFragment = HomeFragment.newInstance(user.id, user.username.toString())
            replaceFragment(homeFragment)
            binding.bottomNav.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.item_home -> replaceFragment(homeFragment)
                    R.id.item_article -> replaceFragment(ArticleFragment())
                    else -> {
                    }
                }
                true
            }
        }

        binding.fab.setOnClickListener {
            fabOnClick()
        }
        binding.fabCamera.setOnClickListener {
            openCamera()
//             Check if the app has camera permission
//            if (checkCameraPermission()) {
//                startCamera()
//            } else {
//                requestCameraPermission()
//            }
        }
        binding.fabGalery.setOnClickListener {
            openGallery()
//            startGallery()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragment.setMenuVisibility(true)
        fragmentTransition.replace(R.id.frame_layout, fragment)
        fragmentTransition.commit()
    }

    private fun fabOnClick() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (clicked) {
            binding.fabCamera.visibility = View.VISIBLE
            binding.fabGalery.visibility = View.VISIBLE
        } else {
            binding.fabCamera.visibility = View.INVISIBLE
            binding.fabGalery.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.fabCamera.startAnimation(fromBottom)
            binding.fabGalery.startAnimation(fromBottom)
            binding.fab.startAnimation(rotateOpen)
        } else {
            binding.fabCamera.startAnimation(toBottom)
            binding.fabGalery.startAnimation(toBottom)
            binding.fab.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding.fabCamera.isClickable = true
            binding.fabGalery.isClickable = true
        } else {
            binding.fabCamera.isClickable = false
            binding.fabGalery.isClickable = false
        }
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                val imageUri = saveBitmapToFile(imageBitmap)
                val intent = Intent(this, PreClassificationActivity::class.java)
                intent.putExtra("imageUri", imageUri)
                startActivity(intent)
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImage: Uri? = result.data?.data
                selectedImage?.let {
                    val intent = Intent(this, PreClassificationActivity::class.java)
                    intent.putExtra("imageUri", it)
                    startActivity(intent)
                }
            }
        }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(cameraIntent)
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    private fun saveBitmapToFile(bitmap: Bitmap): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "JPEG_$timeStamp.jpg"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, fileName)

        try {
            FileOutputStream(imageFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return FileProvider.getUriForFile(
            this,
            "${BuildConfig.APPLICATION_ID}.provider",
            imageFile
        )
    }
}


//    // camera
//    private fun checkCameraPermission(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.CAMERA
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun requestCameraPermission() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.CAMERA),
//            CAMERA_PERMISSION_REQUEST_CODE
//        )
//    }
//
//    @RequiresApi(Build.VERSION_CODES.Q)
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startCamera()
//            } else {
//                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.Q)
//    private fun startCamera() {
//        currentImageUri = getImageUri(this)
//        launcherIntentCamera.launch(currentImageUri)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.Q)
//    private val launcherIntentCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
//        if (isSuccess) {
//            showImage()
//        }
//    }
//
////    private fun checkCameraPermission(): Boolean {
////        return ContextCompat.checkSelfPermission(
////            this,
////            Manifest.permission.CAMERA
////        ) == PackageManager.PERMISSION_GRANTED
////    }
////    private fun requestCameraPermission() {
////        ActivityCompat.requestPermissions(
////            this,
////            arrayOf(Manifest.permission.CAMERA),
////            CAMERA_PERMISSION_REQUEST_CODE
////        )
////    }
////    @RequiresApi(Build.VERSION_CODES.Q)
////    override fun onRequestPermissionsResult(
////        requestCode: Int,
////        permissions: Array<out String>,
////        grantResults: IntArray
////    ) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
////            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                startCamera()
////            } else {
////                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show()
////            }
////        }
////    }
////    @RequiresApi(Build.VERSION_CODES.Q)
////    private fun startCamera() {
////        currentImageUri = getImageUri(this)
////        launcherIntentCamera.launch(currentImageUri)
////    }
////    @RequiresApi(Build.VERSION_CODES.Q)
////    private val launcherIntentCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
////        if (isSuccess) {
////            showImage()
////        }
////    }
//
//    // galery
//    @RequiresApi(Build.VERSION_CODES.Q)
//    private fun startGallery() {
//        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//    }
//    @RequiresApi(Build.VERSION_CODES.Q)
//    private val launcherGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
//        if (uri != null) {
//            currentImageUri = uri
//            showImage()
//        } else {
//            Log.d("Photo Picker", "No media selected")
//        }
//    }
//    @RequiresApi(Build.VERSION_CODES.Q)
//    private fun showImage() {
//        var loadingDialog = LoadingDialog(this)
//        loadingDialog.startLoadingDialog()
//        currentImageUri?.let { uri ->
//            Log.d("Image URI", "showImage: $uri")
//
//            val file = getFileFromUri(uri)
//            file?.let {
//                // Reduce the image file size
//                it.reduceFileImage()
//
//                // Proceed with the reduced image file
//                println("uri: ${uri}")
//                val intent = Intent(this, PreClassificationActivity::class.java)
//                intent.putExtra(EXTRA_IMAGE_URI, uri)
//                startActivity(intent)
//                loadingDialog.dismissDialog()
//            } ?: run {
//                loadingDialog.dismissDialog()
//                Log.e("showImage", "Failed to get file from URI")
//            }
//        }
//    }
//    private fun getFileFromUri(uri: Uri): File? {
//        return try {
//            val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
//            val fileDescriptor = parcelFileDescriptor?.fileDescriptor
//            val file = File(cacheDir, "temp_image_file")
//            file.outputStream().use { outputStream ->
//                fileDescriptor?.let {
//                    FileInputStream(it).use { inputStream ->
//                        inputStream.copyTo(outputStream)
//                    }
//                }
//            }
//            parcelFileDescriptor?.close()
//            file
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.Q)
//    fun File.reduceFileImage(): File {
//        val file = this
//        val bitmap = BitmapFactory.decodeFile(file.path).getRotatedBitmap(file)
//        var compressQuality = 100
//        var streamLength: Int
//        do {
//            val bmpStream = ByteArrayOutputStream()
//            bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
//            val bmpPicByteArray = bmpStream.toByteArray()
//            streamLength = bmpPicByteArray.size
//            compressQuality -= 5
//        } while (streamLength > MAXIMAL_SIZE)
//        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
//        return file
//    }
//    @RequiresApi(Build.VERSION_CODES.Q)
//    fun Bitmap.getRotatedBitmap(file: File): Bitmap? {
//        val orientation = ExifInterface(file).getAttributeInt(
//            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
//        )
//        return when (orientation) {
//            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(this, 90F)
//            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(this, 180F)
//            ExifInterface.ORIENTATION