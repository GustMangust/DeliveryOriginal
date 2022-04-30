package com.Delivery_Project.ui.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.MenuAdapter
import com.Delivery_Project.R
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.databinding.FragmentMenuBinding
import com.Delivery_Project.factory.DishViewModelFactory
import com.Delivery_Project.factory.MenuViewModelFactory
import com.Delivery_Project.repository.DishRepository
import com.Delivery_Project.repository.MenuRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.utility.ConnectionUtility
import com.Delivery_Project.viewModel.DishViewModel
import com.Delivery_Project.viewModel.MenuViewModel


class MenuFragment : Fragment() {
    private val TAG = "MenuActivity"
    private lateinit var binding: FragmentMenuBinding
    lateinit var viewModel: MenuViewModel
    private val categoryAPI = InterfaceAPI.getInstance()
    val adapter = MenuAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.fragment_menu, container, false)
        var databaseHelper = DatabaseHelper(requireContext());
        binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this, MenuViewModelFactory(MenuRepository(categoryAPI))).get(MenuViewModel::class.java)
        binding.recyclerview.adapter = adapter
        updateDatabase()
        adapter.setCategoryList(databaseHelper.getAllCategories())
        return binding.root
    }

    private fun updateDatabase(){
        val interfaceAPI = InterfaceAPI.getInstance()
        if(ConnectionUtility.isOnline(requireContext())) {
            val db = DatabaseHelper(requireContext())
            val dishViewModel =
                ViewModelProvider(this, DishViewModelFactory(DishRepository(interfaceAPI))).get(
                    DishViewModel::class.java
                )
            dishViewModel.dishList.observe(this as LifecycleOwner, Observer {
                db.updateDishes(ArrayList(it))
            })
            dishViewModel.errorMessage.observe(this as LifecycleOwner, Observer {
            })
            dishViewModel.getDish()

            val menuViewModel =
                ViewModelProvider(this, MenuViewModelFactory(MenuRepository(interfaceAPI))).get(
                    MenuViewModel::class.java
                )
            menuViewModel.categoryList.observe(this as LifecycleOwner, Observer {
                db.updateCategories(ArrayList(it))
            })
            menuViewModel.errorMessage.observe(this as LifecycleOwner, Observer {
            })
            menuViewModel.getCategory()
        }
    }

}