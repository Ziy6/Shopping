package com.ziy.shoppingappliaction.Fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ziy.shoppingappliaction.MapsObjects.GeoPoint
import com.ziy.shoppingappliaction.R
import org.json.JSONObject
import org.json.JSONTokener

class MapsFragment : Fragment(), OnMapReadyCallback
{
    lateinit var mapView: MapView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)

        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap)
    {
        val sydney = LatLng(-34.0, 151.0)
        //add marker near sydney
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)) //moving camera to area

        //zoom in 1-world, 5 landmass, 10-City, 15-streets, 20-Buildings <---------
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f))
    }

    //converts string to JSON from Distance API result to get distance between 2 points
    fun getDistance(searchData: String): Double
    {
        val jsonTokener = JSONTokener(searchData).nextValue() as JSONObject
        val jsonArray = jsonTokener.getJSONArray("routes")
        val legsArray = jsonArray.getJSONObject(0).getJSONArray("legs")
        val dis = legsArray.getJSONObject(0).getJSONObject("distance")

        return dis.toString().toDouble()
    }

    //converts string to JSON from Places API result to get Location objects information
    fun getLocations(searchData: String): com.ziy.shoppingappliaction.MapsObjects.Location
    {
        val jsonTokener2 = JSONTokener(searchData).nextValue() as JSONObject   //everything
        val jsonArray2 = jsonTokener2.getJSONArray("results")
        val locale = com.ziy.shoppingappliaction.MapsObjects.Location()

        for (index in 0 until jsonArray2.length())
        {
            val fomAdd = jsonArray2.getJSONObject(index).getString("formatted_address")
            val geom = jsonArray2.getJSONObject(index).getJSONObject("geometry")
            val name = jsonArray2.getJSONObject(index).getString("name")
            val location = geom.getJSONObject("location")
            val lat = location.getString("lat")
            val lng = location.getString("lng")

            locale.setAddress(fomAdd)
            locale.setCalled(name)
            locale.setGeoPoint(lat.toDouble(), lng.toDouble())
        }
        return locale
    }

    //get device location
    fun getDevLocation(): Location?
    {

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        var devLocation: Location?= null
        var deviceLocation: GeoPoint

        //permission checks
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                val lat = location?.latitude
                val lng = location?.longitude

                if(lat != null && lng != null)
                {
                    deviceLocation = GeoPoint(lat, lng)
                }
            }
            return devLocation
        }
        return null
    }

    //get info on route between 2 places in JSON form
    fun getDirectionsUrl(origin: LatLng, destination: LatLng, directionMode: String): String
    {
        val str_origin = "origin=" + origin.latitude + ", " + origin.longitude
        val str_dest = "destination=" + destination.latitude + ", " + destination.longitude
        val mode = "mode=" + directionMode
        var parameters = str_origin + "&" + str_dest+ "&" + mode
        val output = "json"

        val url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters +
                "&key=" + "AIzaSyDKkqd6794bzmY0OpSbzLBeN3cWM0KPo0k"

        return url
    }

    //Toasting
    fun message(text: String)
    {
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(requireContext(), text, duration)
        toast.show()
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}