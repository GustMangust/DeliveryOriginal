package com.Delivery_Project.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.Delivery_Project.databinding.CookOrderItemBinding
import com.Delivery_Project.databinding.DishItemBinding
import com.Delivery_Project.pojo.Order
import com.Delivery_Project.pojo.User
import com.Delivery_Project.ui.ui.activity.DishDescriptionActivity
import com.bumptech.glide.Glide

class AvailableCookOrderAdapter:RecyclerView.Adapter<OrderViewHolder>() {
    var orders = mutableListOf<Order>()

    fun setDishListByStatus(orders: List<Order>,  status: Int) {
        var ordersByStatus = orders.filter {p -> p.Status == status}
        this.orders = ordersByStatus.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CookOrderItemBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.cookOrderId.text = "Order ID: " + order.Id.toString()
        holder.binding.cookNumberOfDishes.text = "Number of dishes: " + order.Dishes.size.toString()
        }

    override fun getItemCount(): Int {
        return orders.size
    }
}
class OrderViewHolder(val binding: CookOrderItemBinding) : RecyclerView.ViewHolder(binding.root) {

}