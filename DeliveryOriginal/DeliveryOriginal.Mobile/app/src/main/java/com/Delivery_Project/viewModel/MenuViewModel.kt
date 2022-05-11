package com.Delivery_Project.viewModel

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.database.DatabaseHelper
import com.Delivery_Project.factory.DishViewModelFactory
import com.Delivery_Project.factory.MenuViewModelFactory
import com.Delivery_Project.pojo.Category
import com.Delivery_Project.repository.DishRepository
import com.Delivery_Project.repository.MenuRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.ui.ui.activity.MainActivity
import com.Delivery_Project.ui.ui.fragment.MenuFragment
import com.Delivery_Project.utility.ConnectionUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.acl.Owner

class MenuViewModel constructor(private val repository: MenuRepository): ViewModel(){
    val categoryList = MutableLiveData<List<Category>>()
    val errorMessage = MutableLiveData<String>()

    fun getCategory() {
        val response = repository.getCategory()
        response.enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                categoryList.postValue(response.body())
            }
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
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
            dishViewModel.dishList.observe(owner as LifecycleOwner, Observer {
                db.updateDishes(ArrayList(it))
            })
            dishViewModel.errorMessage.observe(owner as LifecycleOwner, Observer {
            })
            dishViewModel.getDish()

            val menuViewModel =
                ViewModelProvider(owner, MenuViewModelFactory(MenuRepository(interfaceAPI))).get(
                    MenuViewModel::class.java
                )
            menuViewModel.categoryList.observe(owner as LifecycleOwner, Observer {
                db.updateCategories(ArrayList(it))
            })
            menuViewModel.errorMessage.observe(owner as LifecycleOwner, Observer {
            })
            menuViewModel.getCategory()
        }
    }
}