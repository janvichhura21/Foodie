package com.example.foodie.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.foodie.R
import com.example.foodie.data.CartItem
import com.example.foodie.databinding.ActivityDetailBinding
import com.example.foodie.databinding.ActivityListBinding
import com.google.firebase.auth.FirebaseAuth

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private val viewModel:DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this ,R.layout.activity_detail)
        binding.root

        val name=intent.getStringExtra("name")
        val pic=intent.getStringExtra("pic")
        val price=intent.getStringExtra("price")
        val instructions=intent.getStringExtra("instructions")
        val video=intent.getStringExtra("video")

        binding.youtube.setOnClickListener {
            val intent=Intent(this,YoutubeActivity::class.java)
            intent.putExtra("link",video)
            startActivity(intent)
        }
        binding.dishImage.text=name
        binding.instructions.text=instructions
        binding.price.text= "₹"+price
       Glide.with(this)
           .load(pic)
           .into(binding.dishImage1)

        binding.add.setOnClickListener {
            val breakfastItems =CartItem(FirebaseAuth.getInstance().currentUser!!.uid,name!!, price!!, pic!!,1)
            viewModel.setCartData(breakfastItems)
            Toast.makeText(this, "your cart is added", Toast.LENGTH_SHORT).show()
        }
    }
}