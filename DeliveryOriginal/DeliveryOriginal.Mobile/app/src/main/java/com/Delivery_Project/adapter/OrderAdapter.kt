package com.Delivery_Project.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.Delivery_Project.R
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.databinding.OrderItemBinding
import com.Delivery_Project.model.CartModel
import com.Delivery_Project.ui.ui.fragment.CartFragment
import com.bumptech.glide.Glide


class OrderAdapter(context: Context, view: View): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private var cartList: ArrayList<CartModel> = ArrayList()
    private val context:Context = context
    private val view:View = view

    fun addItems(items: ArrayList<CartModel>){
        this.cartList = items.distinctBy{ it.dishId } as ArrayList<CartModel>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderItemBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val db = DatabaseHelper(context);

        val order = cartList[position]
        holder.binding.orderName.text = order.name
        holder.binding.orderCost.text = order.cost.toString()
        holder.binding.orderAmount.text = db.amountIdenticalDishes(order).toString()

        holder.binding.increaseAmount.setOnClickListener() {
            db.insertOrder(order)
            val amount = Integer.parseInt(holder.binding.orderAmount.text.toString());
            holder.binding.orderAmount.text = (amount + 1).toString()
            setTotalCost(db)
        }

        holder.binding.decreaseAmount.setOnClickListener() {
            if(Integer.parseInt(holder.binding.orderAmount.text.toString()) > 1 ){
                db.deleteOrder(order)
                val amount = Integer.parseInt(holder.binding.orderAmount.text.toString());
                holder.binding.orderAmount.text = (amount - 1).toString()
                setTotalCost(db)
            }
        }

        Glide.with(holder.itemView.context).load(order.image).into(holder.binding.orderImage)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    class OrderViewHolder(var binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root){
    }

    fun setTotalCost(db: DatabaseHelper){
        var totalCost: TextView = view.findViewById(R.id.total_cost)
        totalCost.text = db.getTotalCost().toString()

    }
}