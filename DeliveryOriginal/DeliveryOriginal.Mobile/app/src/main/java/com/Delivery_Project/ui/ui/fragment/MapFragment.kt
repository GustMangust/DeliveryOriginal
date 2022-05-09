package com.Delivery_Project.ui.ui.fragment

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.Delivery_Project.R
import com.Delivery_Project.adapter.TakenDeliveryOrderAdapter
import com.Delivery_Project.databinding.FragmentMapsBinding
import com.Delivery_Project.factory.OrderViewModelFactory
import com.Delivery_Project.pojo.Order
import com.Delivery_Project.repository.OrderRepository
import com.Delivery_Project.retrofit.InterfaceAPI
import com.Delivery_Project.ui.ui.activity.DeliveryActivity
import com.Delivery_Project.utility.SharedPreferencesUtility
import com.Delivery_Project.viewModel.OrderViewModel
import com.google.firebase.firestore.GeoPoint
import java.io.IOException
import java.util.*


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapsBinding
    lateinit var viewModel: OrderViewModel
    private val interfaceAPI = InterfaceAPI.getInstance()
    private lateinit var myWebView: WebView
    val adapter = TakenDeliveryOrderAdapter()
    private  var geopointList: ArrayList<GeoPoint?> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View  = inflater.inflate(R.layout.fragment_maps, container, false)
        binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        myWebView = view.findViewById(R.id.webViewMap)
        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.allowContentAccess = true
        myWebView.settings.domStorageEnabled = true
        myWebView.settings.useWideViewPort = true

        val activity = activity as DeliveryActivity
        val user = activity.getUser()
        viewModel = ViewModelProvider(this, OrderViewModelFactory(OrderRepository(interfaceAPI))).get(
            OrderViewModel::class.java)

        viewModel.orderList.observe(viewLifecycleOwner, Observer {
            getString(ArrayList(it).filter { it?.CurrentEmployee?.Id == user.Id && it.Status == 4 } as ArrayList)
            if(!geopointList.isEmpty()){
                val origin = getAddressFromLocation(GeoPoint(SharedPreferencesUtility.getLatitude(requireContext()).toDouble(),SharedPreferencesUtility.getLongitude(requireContext()).toDouble()))
                val destination = getAddressFromLocation(GeoPoint(geopointList.last()?.latitude!!,geopointList.last()?.longitude!!))
                var url = "https://www.google.com/maps/dir/?api=1&origin=" +
                        "$origin" +
                        "&destination=$destination&travelmode=driving"
                for(i in 0 until (geopointList.size-1)){
                    if(i==0){
                        url += "&waypoints="
                    }
                    val address = getAddressFromLocation(GeoPoint(geopointList.get(i)?.latitude!!,geopointList.get(i)?.longitude!!))
                    url += "$address%7C"
                }
                myWebView.loadUrl(url)
            } else {
                Toast.makeText(requireContext(), "You have no correct orders",Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
        })
        viewModel.getOrder()

    }


    fun getString(orderList : ArrayList<Order>){
        for(order in orderList){
            val location = getLocationFromAddress(order.Address)
            if(location == null){
                Toast.makeText(requireContext(), "Order address with Id:${order.Id} is incorrect",Toast.LENGTH_SHORT).show()
            } else {
                geopointList.add(location!!)
            }
        }
        if(geopointList.size >= 2){
            sortLocations(geopointList,SharedPreferencesUtility.getLatitude(requireContext()).toDouble(), SharedPreferencesUtility.getLongitude(requireContext()).toDouble())
        }
    }

    fun getLocationFromAddress(strAddress: String?): GeoPoint? {
        val coder = Geocoder(requireContext())
        val address: List<Address>?
        var p1: GeoPoint? = null
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null || address.isEmpty()) {
                return null
            }
            val location: Address = address[0]
            p1 = GeoPoint(
                (location.getLatitude()),
                (location.getLongitude())
            )
            return p1
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun getAddressFromLocation(geoPoint: GeoPoint?): String? {
        val coder = Geocoder(requireContext())
        var address  =""
        var p1: GeoPoint? = null
        try {
            address = coder.getFromLocation(geoPoint!!.latitude,geoPoint!!.longitude, 1)[0].getAddressLine(0)


            return address
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun sortLocations(
        locations: List<GeoPoint?>,
        myLatitude: Double,
        myLongitude: Double
    ): ArrayList<GeoPoint?>? {
        val comp: Comparator<GeoPoint?> = Comparator<GeoPoint?> { o, o2 ->
            val result1 = FloatArray(3)
            Location.distanceBetween(myLatitude, myLongitude, o.latitude, o.longitude, result1)
            val distance1 = result1[0]
            val result2 = FloatArray(3)
            Location.distanceBetween(myLatitude, myLongitude, o2.latitude, o2.longitude, result2)
            val distance2 = result2[0]
            distance1.compareTo(distance2)
        }
        Collections.sort(locations, comp)
        return ArrayList(locations)
    }

}
