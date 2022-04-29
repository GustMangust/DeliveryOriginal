package com.Delivery_Project.ui.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.Delivery_Project.R
import com.Delivery_Project.adapter.CookViewPagerAdapter
import com.Delivery_Project.pojo.User
import com.Delivery_Project.ui.ui.fragment.AvailableCookOrdersFragment
import com.Delivery_Project.ui.ui.fragment.AvailableDeliveryOrdersFragment
import com.Delivery_Project.ui.ui.fragment.TakenCookOrdersFragment
import com.Delivery_Project.ui.ui.fragment.TakenDeliveryOrdersFragment
import com.google.android.material.tabs.TabLayout

class DeliveryActivity: AppCompatActivity() {
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)
        setUpTabs()
        user  = intent.getSerializableExtra("User") as User
    }
    private fun setUpTabs(){
        val adapter = CookViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AvailableDeliveryOrdersFragment(),"Available orders")
        adapter.addFragment(TakenDeliveryOrdersFragment(), "Taken orders")
        val viewPager = findViewById<ViewPager>(R.id.deliveryViewPager)
        viewPager.adapter = adapter
        val tabs = findViewById<TabLayout>(R.id.deliveryTabs)
        tabs.setupWithViewPager(viewPager)

    }

    fun getUser() : User{
        return user;
    }
}