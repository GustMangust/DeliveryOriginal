package com.Delivery_Project.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.COST_DOLLAR
import com.Delivery_Project.databinding.DishItemBinding
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.ui.ui.activity.DishDescriptionActivity
import com.bumptech.glide.Glide

class RandomDishAdapter : RecyclerView.Adapter<RandomDishViewHolder>() {
    var dishes = mutableListOf<Dish>()
    fun setRandomDishList(dishes: List<Dish>) {
        this.dishes = dishes.toMutableList()

        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomDishViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DishItemBinding.inflate(inflater, parent, false)
        return RandomDishViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RandomDishViewHolder, position: Int) {
        val dishes = dishes[position]
        holder.binding.dishName.text = dishes.Name
        holder.binding.dishCost.text = dishes.Cost.toString() + COST_DOLLAR
        Glide.with(holder.itemView.context).load(dishes.ImageUrl).into(holder.binding.dishImage)
        holder.binding.dishImage.setOnClickListener() {
                v: View -> Unit
            val context = holder.itemView.context
            val intent = Intent(context, DishDescriptionActivity::class.java)
            intent.putExtra("Dish",dishes)
            ContextCompat.startActivity(context, intent, null)
        }
    }
    override fun getItemCount(): Int {
        return dishes.size
    }
}
class RandomDishViewHolder(val binding: DishItemBinding) : RecyclerView.ViewHolder(binding.root) {

}