package com.example.foodie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodie.R
import com.example.foodie.data.BreakfastItem
import com.example.foodie.databinding.ActivityListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding
    lateinit var breakfastItemAdapter: BreakfastItemAdapter
    private val viewModel:DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_list)
        binding.root
        viewModel.getItems()
    getItems()

    }

    private fun getItems() {
        viewModel.dataChange.observe(this, Observer {
            Log.d("list",it.toString())
            breakfastItemAdapter= BreakfastItemAdapter(this,it)
            binding.rv
                .apply {
                    adapter=breakfastItemAdapter
                    layoutManager=GridLayoutManager(this@ListActivity,2)
                }
        })
    }
}