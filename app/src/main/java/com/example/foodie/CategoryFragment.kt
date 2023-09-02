package com.example.foodie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodie.databinding.FragmentCartBinding
import com.example.foodie.databinding.FragmentCategoryBinding
import com.example.foodie.detail.CategoryAdapter
import com.example.foodie.detail.DetailViewModel
import com.google.firebase.database.DatabaseReference


class CategoryFragment : Fragment() {
    lateinit var binding: FragmentCategoryBinding
    lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: DetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_category,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getItems()
        viewModel.getDinnerItems()
        viewModel.getLunchItems()
        getItems()
    }

    private fun getItems() {
       viewModel.combinedItems.observe(viewLifecycleOwner, Observer {
           categoryAdapter= CategoryAdapter(requireContext(),it)
           binding.categoryrecyclerview.apply {
               layoutManager= GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
               adapter=categoryAdapter
           }
       })
    }
}