package com.Delivery_Project.ui.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.R
import com.Delivery_Project.adapter.TakenDeliveryOrderAdapter
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.TAKEN_DELIVERY_ORDERS_TAG
import com.Delivery_Project.databinding.FragmentTakenDeliveryOrdersBinding
import com.Delivery_Project.factory.OrderViewModelFactory
import com.Delivery_Project.repository.OrderRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.ui.ui.activity.DeliveryActivity
import com.Delivery_Project.ui.ui.activity.LoginActivity
import com.Delivery_Project.utility.SharedPreferencesUtility
import com.Delivery_Project.viewModel.OrderViewModel

class TakenDeliveryOrdersFragment : Fragment() {
    private lateinit var binding: FragmentTakenDeliveryOrdersBinding
    lateinit var viewModel: OrderViewModel
    private val interfaceAPI = InterfaceAPI.getInstance()
    val adapter = TakenDeliveryOrderAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View  = inflater.inflate(R.layout.fragment_taken_delivery_orders, container, false)
        binding = FragmentTakenDeliveryOrdersBinding.inflate(layoutInflater, container, false)
        val activity = activity as DeliveryActivity
        val user = activity.getUser()
        binding.currentDeliveryTakenOrder.text = user.Login
        binding.deliveryLogoutTakenOrder.setOnClickListener {
            logout()
        }
        viewModel = ViewModelProvider(this, OrderViewModelFactory(OrderRepository(interfaceAPI))).get(
            OrderViewModel::class.java)
        binding.recyclerviewTakenDeliveryOrders.adapter = adapter
        viewModel.orderList.observe(viewLifecycleOwner, Observer {
            Log.d(TAKEN_DELIVERY_ORDERS_TAG, "onCreate: $it")
            adapter.setDishListByStatusAndEmployee(it, 4, user)
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