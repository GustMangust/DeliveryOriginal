package com.Delivery_Project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Delivery_Project.databinding.OrderItemBinding
import com.Delivery_Project.model.CartModel
import com.bumptech.glide.Glide

class OrderAdapter: RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private var cartList: ArrayList<CartModel> = ArrayList()

    fun addItems(items: ArrayList<CartModel>){
        this.cartList = items
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderItemBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = cartList[position]
        holder.binding.orderName.text = order.name
        holder.binding.orderCost.text = order.cost.toString()
        //holder.binding.orderAmount.text = order.amount.toString()
        Glide.with(holder.itemView.context).load(order.image).into(holder.binding.orderImage)
        /*var helper: OrderHelper = OrderHelper()
        var totalCost = helper.getTotalCost()*/


    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    class OrderViewHolder(var binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root){
    }

}