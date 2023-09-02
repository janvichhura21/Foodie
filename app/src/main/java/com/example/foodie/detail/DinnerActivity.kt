package com.example.foodie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodie.R
import com.example.foodie.databinding.ActivityDinnerBinding
import com.example.foodie.databinding.ActivityLunchBinding

class DinnerActivity : AppCompatActivity() {
    lateinit var binding: ActivityDinnerBinding
    lateinit var breakfastItemAdapter: BreakfastItemAdapter
    private val viewModel:DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_dinner)
        binding.root
        viewModel.getDinnerItems()
        getItems()
    }
    private fun getItems() {
        viewModel.dinner.observe(this, Observer {
            Log.d("list",it.toString())
            breakfastItemAdapter= BreakfastItemAdapter(this,it)
            binding.rv
                .apply {
                    adapter=breakfastItemAdapter
                    layoutManager= GridLayoutManager(this@DinnerActivity,2)
                }
        })
    }
}