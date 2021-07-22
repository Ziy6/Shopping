package com.ziy.shoppingappliaction.Helpers

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.ziy.shoppingappliaction.MapsObjects.GeoPoint
import java.lang.Exception

class MapsHelper(val context: Context)
{
    val geocoder = Geocoder(context)

    //getting AddressList
    fun getAddress(lat: Double, lan: Double): MutableList<Address>?
    {
        return geocoder.getFromLocation(lat, lan, 1)
    }

    //getting smaller subsection of Addresses
    fun getStreet(address: Address): String? = address.getAddressLine(0)
    fun getCity(address: Address): String? = address.locality
    fun getState(address: Address): String? = address.adminArea
    fun getCountry(address: Address): String? = address.countryName
    fun getPostalCode(address: Address): String? = address.postalCode

    fun getLatLng(address: Address, context: Context): GeoPoint
    {
        val lat = address.latitude
        val lng = address.longitude

        return GeoPoint(lat, lng)
    }

    fun getLocationFromAddress(strAdd: String): GeoPoint?
    {
        var address: List<Address>
        var geoPoint: GeoPoint

        address = geocoder.getFromLocationName(strAdd, 5)
        if(address==null)
        {
            return null
        }
        else
        {
            val location: Address = address.get(0)
            geoPoint = GeoPoint(location.latitude, location.longitude)
            return geoPoint
        }
        return null
    }
}