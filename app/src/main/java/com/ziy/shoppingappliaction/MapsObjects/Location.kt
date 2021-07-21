package com.ziy.shoppingappliaction.MapsObjects

class Location()
{
    lateinit var add: String
    lateinit var  name: String
    lateinit var geoPoint: GeoPoint

    fun setAddress(add: String)
    {
        this.add = add
    }

    fun setCalled(name: String)
    {
        this.name = name
    }

    fun setGeoPoint(lat: Double, lng: Double)
    {
        geoPoint = GeoPoint(lat, lng)
    }

    fun getAddress(): String
    {
        return add
    }

    fun getCalled(): String
    {
        return name
    }

    fun getGeoPnt(): GeoPoint
    {
        return geoPoint
    }
}