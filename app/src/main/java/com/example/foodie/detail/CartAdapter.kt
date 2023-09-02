package com.example.foodie.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.CartFragment
import com.example.foodie.R
import com.example.foodie.data.CartItem
import org.checkerframework.checker.index.qual.LengthOf

class CartAdapter(
    private val context: Context,
    private val cartItems: List<CartItem>,
    val onclick: OnClickListener

) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    var qty=1
    var totalPrice: Int = 0
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.dishName)
        val itemPrice: TextView = itemView.findViewById(R.id.price)
        val quantity: TextView = itemView.findViewById(R.id.count)
        val addButton: AppCompatImageButton = itemView.findViewById(R.id.addelement)
        val removeButton: AppCompatImageButton = itemView.findViewById(R.id.delete)
        val itemImage: ImageView = itemView.findViewById(R.id.dishImage)
    }
    var onQuantityChange: ((position: Int, newQuantity: Int) -> Unit)? = null
    var onRemoveItemClick: ((position: Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.allfooditems, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.itemName.text = cartItem.name
        holder.itemPrice.text = "Price: ₹${cartItem.price}"
        holder.quantity.text= cartItem.quantity.toString()
        Glide.with(context)
            .load(cartItem.pic)
            .into(holder.itemImage)


        holder.itemView.setOnClickListener {
            Log.d("jan","clicked")
        }
        holder.addButton.setOnClickListener {

           onclick.onclick(cartItems,position)
           qty+=1
            if (qty>=1) {

               var price= cartItem.price.toInt()
                totalPrice=price*qty
                Log.d("totalPrice",totalPrice.toString())
                holder.quantity.text = qty.toString()

            }

        }

        holder.removeButton.setOnClickListener {

            qty-=1
            if (qty>=1) {

              //  var totalprice = holder.itemPrice.text.toString().toInt()
                val price = cartItem.price.toInt()
                totalPrice -= price
                holder.quantity.text=qty.toString()
            }
            else if(qty==0){
                Log.d("jan","${qty}clicked")
                onclick.onRemoveClick(cartItems,position)
            }
            /*val newQuantity = cartItem.quantity - 1
            if (newQuantity <= 0) {
                // Remove the item from the cart if the count reaches 0 or below
                onRemoveItemClick?.invoke(position)
            } else {
                Log.d("janu",newQuantity.toString())
                onQuantityChange?.invoke(position, newQuantity)
            }*/
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }


interface OnClickListener{
    fun onclick(cartItems: List<CartItem>,position: Int)
    fun onRemoveClick(cartItems: List<CartItem>,position: Int)
}
    /*interface OnClickListener {
        fun onAddClick(position: Int)
        fun onRemoveClick(position: Int)
    }*/

}
