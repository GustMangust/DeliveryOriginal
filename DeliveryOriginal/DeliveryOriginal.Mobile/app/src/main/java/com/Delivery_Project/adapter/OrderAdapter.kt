package com.Delivery_Project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Delivery_Project.R
import com.Delivery_Project.database.OrderHelper
import com.Delivery_Project.databinding.DishItemBinding
import com.Delivery_Project.databinding.OrderItemBinding
import com.Delivery_Project.model.OrderModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.NonDisposableHandle.parent

class OrderAdapter: RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private var orderList: ArrayList<OrderModel> = ArrayList()

    fun addItems(items: ArrayList<OrderModel>){
        this.orderList = items
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderItemBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.binding.orderName.text = order.name
        holder.binding.orderCost.text = order.cost.toString()
        holder.binding.orderAmount.text = order.amount.toString()
        Glide.with(holder.itemView.context).load(order.image).into(holder.binding.orderImage)
        /*var helper: OrderHelper = OrderHelper()
        var totalCost = helper.getTotalCost()*/


    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrderViewHolder(var binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root){
    }

}