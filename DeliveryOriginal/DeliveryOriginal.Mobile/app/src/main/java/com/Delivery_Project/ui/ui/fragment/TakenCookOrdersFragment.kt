package com.Delivery_Project.ui.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.R
import com.Delivery_Project.adapter.AvailableCookOrderAdapter
import com.Delivery_Project.adapter.TakenCookOrderAdapter
import com.Delivery_Project.databinding.FragmentAvailableCookOrdersBinding
import com.Delivery_Project.databinding.FragmentTakenCookOrdersBinding
import com.Delivery_Project.factory.OrderViewModelFactory
import com.Delivery_Project.repository.OrderRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.ui.ui.activity.CookActivity
import com.Delivery_Project.ui.ui.activity.LoginActivity
import com.Delivery_Project.utility.SharedPreferencesUtility
import com.Delivery_Project.viewModel.OrderViewModel

class TakenCookOrdersFragment : Fragment() {
    private val TAG = "HomeActivity"
    private lateinit var binding: FragmentTakenCookOrdersBinding
    lateinit var viewModel: OrderViewModel
    private val interfaceAPI = InterfaceAPI.getInstance()
    val adapter = TakenCookOrderAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View  = inflater.inflate(R.layout.fragment_taken_cook_orders, container, false)
        binding = FragmentTakenCookOrdersBinding.inflate(layoutInflater, container, false)
        val activity = activity as CookActivity
        val user = activity.getUser()
        binding.currentCookTakenOrder.text = user.Login
        binding.cookLogoutTakenOrder.setOnClickListener {
            logout()
        }
        viewModel = ViewModelProvider(this, OrderViewModelFactory(OrderRepository(interfaceAPI))).get(
            OrderViewModel::class.java)
        binding.recyclerviewTakenCookOrders.adapter = adapter
        viewModel.orderList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setDishListByStatusAndEmployee(it, 2, user)
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
        })
        viewModel.getOrder()
        return binding.root
    }

    fun logout(){
        SharedPreferencesUtility.deleteUser(requireContext())
        startActivity(Intent(requireContext(), LoginActivity::class.java ))
    }
}