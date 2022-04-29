package com.Delivery_Project.ui.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.R
import com.Delivery_Project.adapter.AvailableCookOrderAdapter
import com.Delivery_Project.adapter.RandomDishAdapter
import com.Delivery_Project.databinding.FragmentAvailableCookOrdersBinding
import com.Delivery_Project.databinding.FragmentHomeBinding
import com.Delivery_Project.factory.OrderViewModelFactory
import com.Delivery_Project.factory.RandomDishViewModelFactory
import com.Delivery_Project.repository.OrderRepository
import com.Delivery_Project.repository.RandomDishRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.ui.ui.activity.CookActivity
import com.Delivery_Project.ui.ui.activity.LoginActivity
import com.Delivery_Project.ui.ui.activity.MainActivity
import com.Delivery_Project.utility.ConnectionUtility
import com.Delivery_Project.utility.SharedPreferencesUtility
import com.Delivery_Project.viewModel.OrderViewModel
import com.Delivery_Project.viewModel.RandomDishViewModel

class AvailableCookOrdersFragment : Fragment() {
    private val TAG = "HomeActivity"
    private lateinit var binding: FragmentAvailableCookOrdersBinding
    lateinit var viewModel: OrderViewModel
    private val interfaceAPI = InterfaceAPI.getInstance()
    val adapter = AvailableCookOrderAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View  = inflater.inflate(R.layout.fragment_available_cook_orders, container, false)
        binding = FragmentAvailableCookOrdersBinding.inflate(layoutInflater, container, false)
        val activity = activity as CookActivity
         val user = activity.getUser()
        binding.currentCook.text = user.Login
        binding.cookLogout.setOnClickListener {
            logout()
        }
        viewModel = ViewModelProvider(this, OrderViewModelFactory(OrderRepository(interfaceAPI))).get(
            OrderViewModel::class.java)
        binding.recyclerviewAvailableCookOrders.adapter = adapter
        viewModel.orderList.observe(viewLifecycleOwner, Observer {
                Log.d(TAG, "onCreate: $it")
                adapter.setDishListByStatus(it, 1)
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