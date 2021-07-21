package com.ziy.shoppingappliaction.MapsObjects

class GeoPoint(lat: Double, lng: Double)
{
    private var lat = lat
    private var lng = lng

    fun getLat(): Double = lat
    fun getLng(): Double = lng
}