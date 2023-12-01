package com.example.myandrostory.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myandrostory.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PHOTO_URL = "extra_photo_url"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESC = "extra_desc"
    }

    private lateinit var binding: ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUrl = intent.getStringExtra(EXTRA_PHOTO_URL)
        val name = intent.getStringExtra(EXTRA_NAME)
        val desc = intent.getStringExtra(EXTRA_DESC)

        binding.apply {
            tvNameDetail.text = name
            tvDescDetail.text = desc
            Glide.with(this@DetailStoryActivity)
                .load(photoUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(ivPhotosDetail)
        }
    }
}