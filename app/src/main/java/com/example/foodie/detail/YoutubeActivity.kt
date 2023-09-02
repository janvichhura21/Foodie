package com.example.foodie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.foodie.R
import com.example.foodie.databinding.ActivityDetailBinding
import com.example.foodie.databinding.ActivityYoutubeBinding

class YoutubeActivity : AppCompatActivity() {
    lateinit var binding: ActivityYoutubeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_youtube)
        binding.root
        binding.webView.settings.javaScriptEnabled = true // Enable JavaScript support if needed4

        val link=intent?.getStringExtra("link")
        Log.d("link",link.toString())
        binding.webView.loadUrl(link!!)
    }
}