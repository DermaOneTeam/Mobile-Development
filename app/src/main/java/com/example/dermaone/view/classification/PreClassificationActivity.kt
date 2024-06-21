package com.example.dermaone.view.classification

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.dermaone.R
import com.example.dermaone.api.ApiConfig
import com.example.dermaone.api.responses.PredictResponse
import com.example.dermaone.databinding.ActivityPreClassificationBinding
import com.example.dermaone.view.ViewModelFactory
import com.example.dermaone.view.main.MainActivity
import com.example.dermaone.view.main.MainViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
class PreClassificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreClassificationBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var currentImageUri: Uri? = null
//    private var predictResponseList: List<PredictResponse> = emptyList()
    private var predictResponseList: PredictResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreClassificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentImageUri = intent.getParcelableExtra("imageUri")
        if(currentImageUri != null){
            binding.ivImage.setImageURI(currentImageUri)
            binding.ivImage.tag = currentImageUri
            println("currentImageUri: ${currentImageUri}")
        }
        setAction()
    }
    private fun uriToJpgFile(context: Context, imageUri: Uri): File? {
        val file = File(context.cacheDir, "temp_image.jpg")
        try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val outputStream = FileOutputStream(file)
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            return file
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    private fun predict() {
        var loadingDialog = LoadingDialog(this@PreClassificationActivity)
//        currentImageUri?.let { uri ->
        loadingDialog.startLoadingDialog()
        val imageFile = uriToJpgFile(this, currentImageUri!!)
        Log.d("Image File", "showImage: ${imageFile!!.path}")
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestImageFile
        )
        println("imageFile: ${imageFile}")
        println("requestImageFile: ${requestImageFile}")
        println("multipartBody: ${multipartBody}")
        mainViewModel.getSession().observe(this) { user ->
            try {
                ApiConfig.getApiServicePredict().predict(user.id.toInt(), multipartBody)
                    .enqueue(object : Callback<PredictResponse> {
                        override fun onResponse(
                            call: Call<PredictResponse>,
                            response: Response<PredictResponse>
                        ) {
                            println("onResponse")
                            if (response.isSuccessful) {
                                println("response isSuccessful")
                                val predictResponse = response.body()
                                if (predictResponse!!.error != true) {
                                    predictResponseList = predictResponse
                                    loadingDialog.dismissDialog()
                                    println("Success: ${predictResponse.message}")
                                    val intent = Intent(
                                        this@PreClassificationActivity,
                                        ClassificationActivity::class.java
                                    )
//                                intent.putParcelableArrayListExtra("predictResponse", ArrayList(predictResponseList))
                                    intent.putExtra("predictResponse", predictResponseList)
                                    intent.putExtra("predictImage", currentImageUri)
                                    try {
                                        startActivity(intent)
                                    }catch (e: Exception){
                                        Toast.makeText(this@PreClassificationActivity, "This image cannot be predicted", Toast.LENGTH_LONG).show()
                                        startActivity(Intent(this@PreClassificationActivity, MainActivity::class.java))
                                    }
                                } else {
                                    loadingDialog.dismissDialog()
                                    println("predictResponse = error")
                                    makeToast(predictResponse.message.toString())
                                }
                            } else {
                                println("response not successful: ${response.message()}")
                                loadingDialog.dismissDialog()
                                makeToast(response.message())
                            }
                        }

                        override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                            loadingDialog.dismissDialog()
                            println("onFailure")
                            makeToast(t.message.toString())
                        }
                    })
            }catch (e: Exception){
                makeToast("This image cannot be predicted!")
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
    private fun makeToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setAction(){
        binding.predictBtn.setOnClickListener {
            predict()
        }
        binding.cancelBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}