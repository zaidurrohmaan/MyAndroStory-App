package com.example.myandrostory.ui.add_story

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myandrostory.data.Result
import com.example.myandrostory.databinding.ActivityAddStoryBinding
import com.example.myandrostory.ui.ViewModelFactory
import com.example.myandrostory.ui.story.StoryActivity
import com.example.myandrostory.utils.getImageUri
import com.example.myandrostory.utils.reduceFileImage
import com.example.myandrostory.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@AddStoryActivity)
        val viewModel: AddStoryViewModel by viewModels {
            factory
        }

        binding.btnGaleri.setOnClickListener {
            startGallery()
        }

        binding.btnKamera.setOnClickListener {
            startCamera()
        }

        binding.btnUnggah.setOnClickListener {
            uploadImage()
        }

        viewModel.currentUri.observe(this) {
            binding.ivPreviewImage.setImageURI(it)
            currentImageUri = it
        }

        viewModel.addStoryResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    //val data = result.data
                    val intent = Intent(this@AddStoryActivity, StoryActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }

                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(
                        this@AddStoryActivity,
                        "Upload gagal. Silakan coba lagi", Toast.LENGTH_SHORT
                    ).show()
                }

                else -> showLoading(false)
            }
        }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val factory: ViewModelFactory = ViewModelFactory.getInstance(this@AddStoryActivity)
            val viewModel: AddStoryViewModel by viewModels {
                factory
            }
            currentImageUri = uri
            viewModel.saveUri(uri)
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivPreviewImage.setImageURI(it)
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            val factory: ViewModelFactory = ViewModelFactory.getInstance(this@AddStoryActivity)
            val viewModel: AddStoryViewModel by viewModels {
                factory
            }
            currentImageUri?.let { viewModel.saveUri(it) }
            showImage()
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val description = binding.etDescription.text.toString().trim()
            if (description == "") {
                showToast("Silakan tambahkan deskripsi")
            } else {
                showLoading(true)
                val imageFile = uriToFile(uri, this).reduceFileImage()
                Log.d("Image File", "showImage: ${imageFile.path}")
                val requestBody = description.toRequestBody("text/plain".toMediaType())
                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData(
                    "photo",
                    imageFile.name,
                    requestImageFile
                )
                val factory: ViewModelFactory = ViewModelFactory.getInstance(this@AddStoryActivity)
                val viewModel: AddStoryViewModel by viewModels {
                    factory
                }
                viewModel.uploadStory(multipartBody, requestBody)
            }
        } ?: showToast("Silakan pilih gambar terlebih dahulu")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}