package com.cse3200.final_photo_viewer

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cse3200.final_photo_viewer.databinding.ActivityMainBinding

// Take pictures using INTENT:
// https://developer.android.com/training/camera/camera-intents
// https://www.youtube.com/watch?v=DPHkhamDoyc

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val REQUEST_IMAGE_CAPTURE = 42
    private var previousImageTaken : Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            takePictureIntent()
        }
    }

    private fun takePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Unable to take picture.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageTaken = data?.extras?.get("data")
            binding.recentImage.setImageBitmap(imageTaken as Bitmap?)
            if (previousImageTaken != null) {
                binding.previousImage.setImageBitmap(previousImageTaken as Bitmap?)
            }
            previousImageTaken = imageTaken
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}