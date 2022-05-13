package com.Delivery_Project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Delivery_Project.R
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.databinding.CookOrderDescriptionItemBinding
import com.Delivery_Project.databinding.OrderItemBinding
import com.Delivery_Project.model.CartModel
import com.Delivery_Project.pojo.Dish
import com.bumptech.glide.Glide

class UserOrderDescriptionAdapter(): RecyclerView.Adapter<OrderDescriptionHolder>() {
    private var dishList = mutableListOf<Dish>()


    fun setItems(items: List<Dish>) {
        this.dishList = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDescriptionHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CookOrderDescriptionItemBinding.inflate(inflater, parent, false)
        return OrderDescriptionHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDescriptionHolder, position: Int) {
        val dish = dishList[position]
        holder.binding.cookOrderDescriptionName.text = dish.Name
        holder.binding.cookOrderDescriptionCost.text = "${dish.Cost} $"
        Glide.with(holder.itemView.context).load(dish.ImageUrl)
            .into(holder.binding.cookOrderDescriptionImage)
    }

    override fun getItemCount(): Int {
        return dishList.size
    }
}

class OrderDescriptionHolder(var binding: CookOrderDescriptionItemBinding) : RecyclerView.ViewHolder(binding.root){
}

