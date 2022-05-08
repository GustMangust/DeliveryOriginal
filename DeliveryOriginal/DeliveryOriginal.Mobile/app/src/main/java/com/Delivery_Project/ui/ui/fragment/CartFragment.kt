package com.Delivery_Project.ui.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.Delivery_Project.R
import com.Delivery_Project.adapter.OrderAdapter
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.factory.DishViewModelFactory
import com.Delivery_Project.factory.MenuViewModelFactory
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.repository.DishRepository
import com.Delivery_Project.repository.MenuRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.ui.ui.activity.CheckoutActivity
import com.Delivery_Project.ui.ui.activity.MainActivity
import com.Delivery_Project.utility.ConnectionUtility
import com.Delivery_Project.viewModel.DishViewModel
import com.Delivery_Project.viewModel.MenuViewModel


class CartFragment : Fragment() {
    private var adapter : OrderAdapter? = null
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_cart, container, false)

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var activity = activity as MainActivity

        var user = activity.getUser();

        databaseHelper = DatabaseHelper(requireContext())
        databaseHelper.deleteRemovedDishes()
        var orderList = databaseHelper.getAllOrders(requireContext())
        recyclerView = requireView().findViewById(R.id.recyclerviewOrders)

        //recyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = OrderAdapter(requireContext(), requireView())
        recyclerView.adapter = adapter
        adapter?.addItems(orderList)

        var dishList = arrayListOf<Dish>()
        for(item in orderList){
            dishList.add(item.convertToDish())
        }

        this.setTotalCost()


        var checkoutBtn = requireView().findViewById<Button>(R.id.orderCheckout);
        checkoutBtn.setOnClickListener {
            if(!dishList.isEmpty()){
                val intent = Intent(context, CheckoutActivity::class.java)
                intent.putExtra("Dishes",dishList)
                intent.putExtra("User", user)
                ContextCompat.startActivity(requireContext(), intent, null)
            } else {
                Toast.makeText(requireContext(), "Please, add dish to order", Toast.LENGTH_LONG).show()
            }
        }


    }
    fun setTotalCost(){
        var totalCost: TextView = requireView().findViewById(R.id.total_cost)
        totalCost.text = databaseHelper.getTotalCost().toString()
    }

}