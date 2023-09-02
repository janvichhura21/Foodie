package com.example.foodie.detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.R
import com.example.foodie.data.BreakfastItem
import com.example.foodie.data.CartItem

class CategoryAdapter(
    private val context: Context,
    private val cartItems: List<BreakfastItem>,

) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.categorytxt)
        val itemImage: ImageView = itemView.findViewById(R.id.categoryimage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.catergoryitems, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val breakfastItem = cartItems[position]
        holder.itemName.text = breakfastItem.name
        Glide.with(context)
            .load(breakfastItem.pic)
            .into(holder.itemImage)
        holder.itemView.setOnClickListener {
            val intent= Intent(context,DetailActivity::class.java)
            intent.putExtra("name",breakfastItem.name)
            intent.putExtra("pic",breakfastItem.pic)
            intent.putExtra("price",breakfastItem.price)
            intent.putExtra("instructions",breakfastItem.instructions)
            intent.putExtra("video",breakfastItem.video)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}
