package com.Delivery_Project.ui.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.MenuAdapter
import com.Delivery_Project.R
import com.Delivery_Project.adapter.RandomDishAdapter
import com.Delivery_Project.databinding.FragmentHomeBinding
import com.Delivery_Project.databinding.FragmentMenuBinding
import com.Delivery_Project.factory.MenuViewModelFactory
import com.Delivery_Project.factory.RandomDishViewModelFactory
import com.Delivery_Project.repository.MenuRepository
import com.Delivery_Project.repository.RandomDishRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.viewModel.MenuViewModel
import com.Delivery_Project.viewModel.RandomDishViewModel


class HomeFragment : Fragment() {
    private val TAG = "HomeActivity"
    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: RandomDishViewModel
    private val interfaceAPI = InterfaceAPI.getInstance()
    val adapter = RandomDishAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this, RandomDishViewModelFactory(RandomDishRepository(interfaceAPI))).get(
            RandomDishViewModel::class.java)
        binding.recyclerviewRandomDishes.adapter = adapter
        viewModel.randomDishList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setRandomDishList(it)
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
        })
        viewModel.getRandomDish()

        return binding.root
    }

}