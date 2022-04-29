package com.Delivery_Project.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.Delivery_Project.databinding.CookOrderItemBinding
import com.Delivery_Project.databinding.DishItemBinding
import com.Delivery_Project.pojo.Customer
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.pojo.Order
import com.Delivery_Project.pojo.User
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.ui.ui.activity.CookActivity
import com.Delivery_Project.ui.ui.activity.DishDescriptionActivity
import com.Delivery_Project.utility.SharedPreferencesUtility
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

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
        val context = holder.itemView.context
        val user = SharedPreferencesUtility.getUser(context)
        holder.binding.cookTakeOrder.setOnClickListener { v: View -> Unit
            putMethod(order.Id,2, user!!)
            val intent = Intent(context, CookActivity::class.java)
            intent.putExtra("User",user)
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
class OrderViewHolder(val binding: CookOrderItemBinding) : RecyclerView.ViewHolder(binding.root) {

}