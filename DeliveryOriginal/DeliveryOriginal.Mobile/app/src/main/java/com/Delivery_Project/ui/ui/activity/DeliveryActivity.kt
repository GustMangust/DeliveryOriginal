package com.Delivery_Project.ui.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.Delivery_Project.R
import com.Delivery_Project.adapter.CookViewPagerAdapter
import com.Delivery_Project.constants.Constants
import com.Delivery_Project.pojo.User
import com.Delivery_Project.ui.ui.fragment.*
import com.Delivery_Project.utility.SharedPreferencesUtility
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import java.util.*

class DeliveryActivity: AppCompatActivity() {
    private lateinit var user: User

    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)
        setUpTabs()
        user  = intent.getSerializableExtra("User") as User
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
    }
    private fun setUpTabs(){
        val adapter = CookViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AvailableDeliveryOrdersFragment(),"Available orders")
        adapter.addFragment(TakenDeliveryOrdersFragment(), "Taken orders")
        adapter.addFragment(MapFragment(), "Map")
        val viewPager = findViewById<ViewPager>(R.id.deliveryViewPager)
        viewPager.adapter = adapter
        val tabs = findViewById<TabLayout>(R.id.deliveryTabs)
        tabs.setupWithViewPager(viewPager)

    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null).addOnCompleteListener(this) { task  ->
                    val location: Location? = task.result
                    if (location != null) {
                        SharedPreferencesUtility.saveLocation(location,this)
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            Constants.SharedPreferences.PERMISSION_ID
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.SharedPreferences.PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }
    fun getUser() : User{
        return user
    }
}