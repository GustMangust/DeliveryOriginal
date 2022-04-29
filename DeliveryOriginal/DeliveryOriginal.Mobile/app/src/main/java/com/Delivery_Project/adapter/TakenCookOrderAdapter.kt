package com.Delivery_Project.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.Delivery_Project.databinding.CookOrderItemBinding
import com.Delivery_Project.databinding.CookOrderTakenItemBinding
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.pojo.Order
import com.Delivery_Project.pojo.User
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.ui.ui.activity.CookActivity
import com.Delivery_Project.ui.ui.activity.CookOrderDescription
import com.Delivery_Project.utility.SharedPreferencesUtility
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.Serializable

class TakenCookOrderAdapter : RecyclerView.Adapter<OrderTakenViewHolder>() {
    var orders = mutableListOf<Order>()

    fun setDishListByStatusAndEmployee(orders: List<Order>, status: Int, employee: User) {
        var ordersByStatusAndEmployee = orders.filter {p -> p.Status == status && p.CurrentEmployee == employee}
        this.orders = ordersByStatusAndEmployee.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderTakenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CookOrderTakenItemBinding.inflate(inflater, parent, false)
        return OrderTakenViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderTakenViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.cookOrderId.text = "Order ID: " + order.Id.toString()
        holder.binding.cookNumberOfDishes.text = "Number of dishes: " + order.Dishes.size.toString()
        val context = holder.itemView.context
        val user = SharedPreferencesUtility.getUser(context)
        holder.binding.cookReadyOrderTakenOrder.setOnClickListener { v: View -> Unit
            putMethod(order.Id,3, user!!)
            val intent = Intent(context, CookActivity::class.java)
            intent.putExtra("User",user)
            ContextCompat.startActivity(context, intent, null)
        }
        holder.binding.cookShowOrder.setOnClickListener {v: View -> Unit
            val intent = Intent(context, CookOrderDescription::class.java)
            intent.putExtra("Dishes", order.Dishes)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    fun putMethod(id: Int, status: Int, currentEmployee: User) {
        val jsonObject = JSONObject()
        val jsonEmployee = Gson().toJsonTree(currentEmployee)
        jsonObject.put("Id",id)
        jsonObject.put("PhoneNumber", null)
        jsonObject.put("SubmittedAt", null)
        jsonObject.put("Status", status)
        jsonObject.put("Address", null)
        jsonObject.put("Customer", null)
        jsonObject.put("CurrentEmployee", jsonEmployee)
        jsonObject.put("Dishes", null)
        val jsonObjectString = jsonObject.toString()

        Log.d("Pretty Printed JSON :", jsonObjectString.toString())

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = InterfaceAPI.getInstance().updateOrder(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.code().toString()
                        )
                    )
                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }
}
class OrderTakenViewHolder(val binding: CookOrderTakenItemBinding) : RecyclerView.ViewHolder(binding.root) {

}