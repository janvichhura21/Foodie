package com.example.foodie.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodie.data.BreakfastItem
import com.example.foodie.data.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class DetailViewModel:ViewModel() {
    var dataChange = MutableLiveData<List<BreakfastItem>>()
    var result = MutableLiveData<List<CartItem>>()

    var price = MutableLiveData<String>()
    var lunch = MutableLiveData<List<BreakfastItem>>()
    var dinner = MutableLiveData<List<BreakfastItem>>()
    val combinedItems: MutableLiveData<List<BreakfastItem>> = MutableLiveData()
    fun setData(breakfastItem: BreakfastItem){
        var db= FirebaseFirestore.getInstance()
        val map= hashMapOf(
            "name" to breakfastItem.name,
            "price" to breakfastItem.price,
            "instructions" to breakfastItem.instructions,
            "price" to breakfastItem.price,
            "pic" to breakfastItem.pic,
            "video" to breakfastItem.video,


            )

        db.collection("breakfast_items")
            .document()
            .set(map, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("janvi","Success")

            }.addOnFailureListener {
                Log.d("janvi",it.message.toString())
            }

    }
    fun setCartData(cartItem: CartItem){
        var db= FirebaseFirestore.getInstance()
        val map= hashMapOf(
            "id" to FirebaseAuth.getInstance().currentUser!!.uid,
            "name" to cartItem.name,
            "price" to cartItem.price,
            "pic" to cartItem.pic,
            "quantity" to cartItem.quantity,
            )

        db.collection("Cart")
            .document()
            .set(map, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("janvi","Success")

            }.addOnFailureListener {
                Log.d("janvi",it.message.toString())
            }

    }
    fun getCartItems() {
        var db= FirebaseFirestore.getInstance()
        db.collection("Cart")
            .get()
            .addOnSuccessListener {
                val data = it.toObjects(CartItem::class.java)
                result.value=data

            }
    }
    fun setLunchData(breakfastItem: BreakfastItem){
        var db= FirebaseFirestore.getInstance()
        val map= hashMapOf(
            "id" to FirebaseAuth.getInstance().currentUser!!.uid,
            "name" to breakfastItem.name,
            "price" to breakfastItem.price,
            "instructions" to breakfastItem.instructions,
            "price" to breakfastItem.price,
            "pic" to breakfastItem.pic,
            "video" to breakfastItem.video,


            )

        db.collection("Lunch")
            .document()
            .set(map, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("janvi","Success")

            }.addOnFailureListener {
                Log.d("janvi",it.message.toString())
            }

    }
    fun setDinnerData(breakfastItem: BreakfastItem){
        var db= FirebaseFirestore.getInstance()
        val map= hashMapOf(
            "name" to breakfastItem.name,
            "price" to breakfastItem.price,
            "instructions" to breakfastItem.instructions,
            "price" to breakfastItem.price,
            "pic" to breakfastItem.pic,
            "video" to breakfastItem.video,


            )

        db.collection("Dinner")
            .document()
            .set(map, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("janvi","Success")

            }.addOnFailureListener {
                Log.d("janvi",it.message.toString())
            }

    }
    fun getDinnerItems() {
        var db= FirebaseFirestore.getInstance()
        db.collection("Dinner")
            .get()
            .addOnSuccessListener {
                val data = it.toObjects(BreakfastItem::class.java)
                val combinedList = mutableListOf<BreakfastItem>()
                combinedList.addAll(data) // Add dinner items to the combined list
                combinedList.addAll(lunch.value ?: emptyList()) // Add lunch items
                combinedList.addAll(dataChange.value ?: emptyList()) // Add breakfast items
                combinedItems.value = combinedList
                dinner.value = data
            }
    }
    fun getLunchItems() {
        var db= FirebaseFirestore.getInstance()
        db.collection("Lunch")
            .get()
            .addOnSuccessListener {
                val data = it.toObjects(BreakfastItem::class.java)
                val combinedList = mutableListOf<BreakfastItem>()
                combinedList.addAll(data) // Add lunch items to the combined list
                combinedList.addAll(dinner.value ?: emptyList()) // Add dinner items
                combinedList.addAll(dataChange.value ?: emptyList()) // Add breakfast items
                combinedItems.value = combinedList
                lunch.value = data
            }
    }
    fun getItems() {
        var db= FirebaseFirestore.getInstance()
        db.collection("breakfast_items")
            .get()
            .addOnSuccessListener {
                val data = it.toObjects(BreakfastItem::class.java)
                val combinedList = mutableListOf<BreakfastItem>()
                combinedList.addAll(data) // Add breakfast items to the combined list
                combinedList.addAll(dinner.value ?: emptyList()) // Add dinner items
                combinedList.addAll(lunch.value ?: emptyList()) // Add lunch items
                combinedItems.value = combinedList
                dataChange.value = data
            }
    }
   /* fun deleteCartItem(cartItem: CartItem) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Cart")
            .document(cartItem.id) // Assuming you have a field for the item's unique ID in Firestore
            .delete()
            .addOnSuccessListener {
                Log.d("delete", "Item deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.w("delete", "Error deleting item", e)
            }
    }*/
    fun getPrice(){
       val db = FirebaseFirestore.getInstance()
       db.collection("Cart")
           .whereEqualTo("id",FirebaseAuth.getInstance().uid!!)
           .get()
           .addOnSuccessListener { querySnapshot ->
               val prices = mutableListOf<String>() // Create a list to store prices

               for (document in querySnapshot.documents) {
                   val cartItem = document.toObject(CartItem::class.java)
                   cartItem?.let {
                       // Assuming your CartItem has a 'price' field of type Double
                       prices.add(it.price)
                   }
               }
               Log.d("price",prices.toString())
               price.value = prices.toString() // Set the LiveData to the list of prices
           }
           .addOnFailureListener { e ->
               // Handle any errors here
               Log.w("getPrice", "Error getting prices", e)
           }
    }
   fun deleteDetails(){
       val db = FirebaseFirestore.getInstance()
       val query=db.collection("Cart")
           .whereEqualTo("id",FirebaseAuth.getInstance().uid!!)
           .get()
       query.addOnSuccessListener {
           for (document in it){
               db.collection("Cart").document(document.id)
                   .delete()
                   .addOnSuccessListener {
                       Log.d("success","successfully  deleted")
                   }.addOnFailureListener {
                       Log.d("unSuccess","successfully  deleted")
                   }
           }
       }
   }



}