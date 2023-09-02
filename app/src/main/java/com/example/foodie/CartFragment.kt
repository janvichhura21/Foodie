package com.example.foodie

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodie.data.CartItem
import com.example.foodie.databinding.FragmentCartBinding
import com.example.foodie.detail.CartAdapter
import com.example.foodie.detail.DetailViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CartFragment : Fragment(), CartAdapter.OnClickListener {
    lateinit var binding: FragmentCartBinding
    private lateinit var databaseReference: DatabaseReference
    private val cartItems = mutableListOf<CartItem>()
    private lateinit var cartAdapter: CartAdapter
    private val viewModel: DetailViewModel by viewModels()
    var uri: Uri? = null
    var dp: String = ""

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartAdapter = CartAdapter(requireContext(),cartItems,this)
        viewModel.getCartItems()
        viewModel.getPrice()

            getItems()

        binding.confirm.setOnClickListener {
            val message: String? = "Are you sure to confirm Order"
            showCustomDialogBox(message)
        }
        //setListeners()
       /* binding.button.setOnClickListener {
            getBreakFast()
        }*/


    }

    private fun getItems() {
        viewModel.price.observe(viewLifecycleOwner, Observer {c->
            var a=0
            Log.d("total",c)
            val data=listOf(c)
            for (element in data) {
                    val price = element.toIntOrNull()

                    if (price != null) {
                        a += price
                        Log.d("price1", a.toString())

                }


            }
            Log.d("price1", a.toString())
           binding.price.text=a.toString()
            val value= a +65
            binding.price2.text= value.toString()
        })
        viewModel.result.observe(viewLifecycleOwner, Observer {

         //   cartItems.clear()
            cartItems.addAll(it)
            if (cartItems.isEmpty()){
                binding.rl.visibility=View.GONE
            }else
                binding.rl.visibility=View.VISIBLE
            // Initialize or update the cartAdapter with the updated cartItems
            cartAdapter.notifyDataSetChanged()
            binding.cartRv.apply {

             //   cartItems.addAll(it)
                adapter=cartAdapter
                layoutManager=LinearLayoutManager(requireContext())
            }
        })
    }



    override fun onclick(cartItems: List<CartItem>,position: Int) {
        Log.d("kk","item clicked")

        Log.d("price","${cartItems[position].price}")
        var qty = 1
        var totalPrice: Int = 0
        qty += 1
        if (qty >= 1) {
            val data=cartItems[position]
            var price =  data.price.toInt()
            totalPrice = price * qty
            binding.price.text=totalPrice.toString()
            val total=totalPrice+65
            binding.price2.text=total.toString()
        }


    }

    override fun onRemoveClick(cartItems: List<CartItem>,position: Int) {
        val data=cartItems[position]
            Log.d("item","${data.name}delete items")
            Toast.makeText(requireContext(), "delete item", Toast.LENGTH_SHORT).show()
            viewModel.deleteDetails()
             cartAdapter.notifyItemRemoved(position)

    }
    private fun showCustomDialogBox(message: String?) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)

        tvMessage.text = message

        btnYes.setOnClickListener {
           showCustomDialogBox1("Order is confirmed")
            //dialog.dismiss()
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun showCustomDialogBox1(message: String?) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog1)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
        tvMessage.text = message
        dialog.show()


    }
}







