package com.example.foodie.detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.R
import com.example.foodie.data.BreakfastItem
import com.squareup.picasso.Picasso

class BreakfastItemAdapter(val context: Context,private val breakfastItems: List<BreakfastItem>) :
    RecyclerView.Adapter<BreakfastItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.dishName)
        val itemImageView: ImageView = itemView.findViewById(R.id.dishImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val breakfastItem = breakfastItems[position]
        holder.itemNameTextView.text = breakfastItem.name
        Glide.with(context)
            .load(breakfastItem.pic)
            .into(holder.itemImageView)

        holder.itemView.setOnClickListener {
            val intent=Intent(context,DetailActivity::class.java)
            intent.putExtra("name",breakfastItem.name)
            intent.putExtra("pic",breakfastItem.pic)
            intent.putExtra("price",breakfastItem.price)
            intent.putExtra("instructions",breakfastItem.instructions)
            intent.putExtra("video",breakfastItem.video)
            context.startActivity(intent)
        }

        // Load the image using a library like Picasso or Glide
        // Replace 'R.id.itemImageView' with the ID of your ImageView
   //     Picasso.get().load(breakfastItem.pic).into(holder.itemImageView)
    }

    override fun getItemCount(): Int {
        return breakfastItems.size
    }
}
