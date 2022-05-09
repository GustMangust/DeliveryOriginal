package com.Delivery_Project.viewModel

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.factory.DishViewModelFactory
import com.Delivery_Project.factory.MenuViewModelFactory
import com.Delivery_Project.pojo.Category
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.repository.DishRepository
import com.Delivery_Project.repository.MenuRepository
import com.Delivery_Project.repository.RandomDishRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.utility.ConnectionUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomDishViewModel constructor(private val repository:RandomDishRepository): ViewModel(){
    val randomDishList = MutableLiveData<List<Dish>>()
    val errorMessage = MutableLiveData<String>()

    fun getRandomDish() {
        val response = repository.getRandomDish()
        response.enqueue(object : Callback<List<Dish>> {
            override fun onResponse(call: Call<List<Dish>>, response: Response<List<Dish>>) {
                randomDishList.postValue(response.body())
            }
            override fun onFailure(call: Call<List<Dish>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
     fun updateDatabase(context: Context, owner: Fragment){
        val interfaceAPI = InterfaceAPI.getInstance()
        if(ConnectionUtility.isOnline(context)) {
            val db = DatabaseHelper(context)
            val dishViewModel =
                ViewModelProvider(owner, DishViewModelFactory(DishRepository(interfaceAPI))).get(
                    DishViewModel::class.java
                )
            dishViewModel.dishList.observe(owner, Observer {
                db.updateDishes(ArrayList(it))
            })
            dishViewModel.errorMessage.observe(owner, Observer {
            })
            dishViewModel.getDish()

            val menuViewModel =
                ViewModelProvider(owner, MenuViewModelFactory(MenuRepository(interfaceAPI))).get(
                    MenuViewModel::class.java
                )
            menuViewModel.categoryList.observe(owner, Observer {
                db.updateCategories(ArrayList(it))
            })
            menuViewModel.errorMessage.observe(owner, Observer {
            })
            menuViewModel.getCategory()
        }
    }

}