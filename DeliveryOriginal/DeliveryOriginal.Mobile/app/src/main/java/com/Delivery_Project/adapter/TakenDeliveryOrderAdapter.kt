package com.Delivery_Project.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.Delivery_Project.databinding.DeliveryOrderTakenItemBinding
import com.Delivery_Project.pojo.Order
import com.Delivery_Project.pojo.User
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.ui.ui.activity.UserOrderDescription
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

class TakenDeliveryOrderAdapter : RecyclerView.Adapter<DeliveryTakenViewHolder>() {
    var orders = mutableListOf<Order>()

    fun setDishListByStatusAndEmployee(orders: List<Order>, status: Int, employee: User) {
        var ordersByStatusAndEmployee = orders.filter {p -> p.Status == status && p.CurrentEmployee == employee}
        this.orders = ordersByStatusAndEmployee.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryTakenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DeliveryOrderTakenItemBinding.inflate(inflater, parent, false)
        return DeliveryTakenViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DeliveryTakenViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.userOrderId.text = "Order ID: " + order.Id.toString()
        holder.binding.userNumberOfDishes.text = "Number of dishes: " + order.Dishes.size.toString()
        val context = holder.itemView.context
        val user = SharedPreferencesUtility.getUser(context)
        holder.binding.userReadyOrderTakenOrder.setOnClickListener { v: View -> Unit
            putMethod(order.Id,5, user!!)
            orders.remove(order)
            notifyDataSetChanged()
        }
        holder.binding.userShowOrder.setOnClickListener {v: View -> Unit
            var finalCost = order.Dishes.sumByDouble { it.Cost }
            val intent = Intent(context, UserOrderDescription::class.java)
            intent.putExtra("Dishes", order.Dishes)
            intent.putExtra("PhoneNumber", order.PhoneNumber)
            intent.putExtra("Customer", order.Customer)
            intent.putExtra("Address", order.Address)
            intent.putExtra("TotalCost", finalCost)
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
class DeliveryTakenViewHolder(val binding: DeliveryOrderTakenItemBinding) : RecyclerView.ViewHolder(binding.root) {

}