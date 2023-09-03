package com.example.foodie

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodie.databinding.FragmentHomeBinding
import com.example.foodie.detail.DetailViewModel
import com.example.foodie.detail.DinnerActivity
import com.example.foodie.detail.ListActivity
import com.example.foodie.detail.LunchActivity
import com.example.foodie.detail.MyAdapter


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var myadapter: MyAdapter
    private val viewModel: DetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getItems()
        viewModel.getLunchItems()
        viewModel.getDinnerItems()

        getBreakfast()
        getItems()
    }

    private fun getItems() {

        viewModel.combinedItems.observe(viewLifecycleOwner, Observer {
            val spinnerAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item)
            spinnerAdapter.add("Sort By")
            var a=true
            var b=false
            it.forEach {
                if (it.t){
                   b=true
                }else{
                    a=false
                }

            }
            if (b){
                spinnerAdapter.add("Veg")
            }
            if (!a){
                spinnerAdapter.add("Non Veg")
            }

            binding.sortSpinner.setAdapter(spinnerAdapter)
            binding.sortSpinner.setSelection(0)
            binding.sortSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    // Get the selected item from the spinner
                    val selectedOption = parentView?.getItemAtPosition(position).toString()
                    var a=""
                    // Perform actions based on the selected option
                    when (selectedOption) {
                        "Sort By" -> {

                            myadapter.updateData(it)
                        }

                        "Veg" -> {
                            val filteredItems = it.filter {it.t
                            }
                            myadapter.updateData(filteredItems)

                        }
                        "Non Veg" -> {
                            val filteredItems = it.filter {it.t.not()
                            }
                            myadapter.updateData(filteredItems)
                        }
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // Handle the case where nothing is selected (if needed)
                }
            })

            myadapter= MyAdapter(requireContext(),it)
            binding.rv1
                .apply {
                    adapter=myadapter
                    layoutManager= LinearLayoutManager(requireContext())
                }
            binding.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Not needed for this example
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Filter the data based on the search query
                    val searchQuery = s.toString().trim()
                    val filteredList = it.filter { item ->
                        item.name.contains(searchQuery, ignoreCase = true)
                        // Replace 'item.name' with the field you want to search in
                    }
                    myadapter.updateData(filteredList) // Update the RecyclerView with filtered data
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed for this example
                }
            })
        })


    }

    private fun getBreakfast() {
        binding.breakfastBtn.setOnClickListener {
            startActivity(Intent(requireContext(),ListActivity::class.java))

        }
        binding.SnacksBtn.setOnClickListener {
            startActivity(Intent(requireContext(),LunchActivity::class.java))

        }

        binding.dinnerBtn.setOnClickListener {
            startActivity(Intent(requireContext(),DinnerActivity::class.java))

        }
    }
}