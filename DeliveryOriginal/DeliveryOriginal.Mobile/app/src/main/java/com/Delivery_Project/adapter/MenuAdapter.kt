package com.Delivery_Project

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.Delivery_Project.databinding.ActivityMainBinding
import com.Delivery_Project.databinding.CategoryItemBinding
import com.Delivery_Project.databinding.FragmentMenuBinding
import com.Delivery_Project.pojo.Category
import com.Delivery_Project.ui.ui.activity.DishesActivity
import com.Delivery_Project.ui.ui.activity.LoginActivity
import com.Delivery_Project.ui.ui.activity.MainActivity
import com.bumptech.glide.Glide
import java.util.stream.DoubleStream.builder

class MenuAdapter : RecyclerView.Adapter<MenuViewHolder>() {
    var categories = mutableListOf<Category>()
    //var sortedCateories = categories.filter { p -> categories.any { p.Id ==  } }
    fun setCategoryList(categories: List<Category>) {
        this.categories = categories.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return MenuViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.name.text = category.Name
        Glide.with(holder.itemView.context).load(category.ImageUrl).into(holder.binding.categoryImage)
        holder.binding.categoryImage.setOnClickListener {
            v: View -> Unit
            val context = holder.itemView.context
            val intent = Intent(context, DishesActivity::class.java)
            val categoryId = category.Id
            intent.putExtra("Category_Id",categoryId)
            startActivity(context,intent, null)
        }



    }
    override fun getItemCount(): Int {
        return categories.size
    }
}
class MenuViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

}