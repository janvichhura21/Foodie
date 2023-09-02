package com.example.foodie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodie.R
import com.example.foodie.databinding.ActivityListBinding
import com.example.foodie.databinding.ActivityLunchBinding

class LunchActivity : AppCompatActivity() {
    lateinit var binding: ActivityLunchBinding
    lateinit var breakfastItemAdapter: BreakfastItemAdapter
    private val viewModel:DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_lunch)
        binding.root
        viewModel.getLunchItems()
        getItems()

    }
    private fun getItems() {
        viewModel.lunch.observe(this, Observer {
            Log.d("list",it.toString())
            breakfastItemAdapter= BreakfastItemAdapter(this,it)
            binding.rv
                .apply {
                    adapter=breakfastItemAdapter
                    layoutManager= GridLayoutManager(this@LunchActivity,2)
                }
        })
    }
}