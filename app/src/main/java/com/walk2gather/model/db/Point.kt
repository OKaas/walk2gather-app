package com.walk2gather.model.db

import com.google.firebase.database.PropertyName

data class Point (

    var uid: String,

    @get:PropertyName("point")
    @set:PropertyName("point")
    var points: ArrayList<Float>? = null

){

    companion object Path {
        val PATH = Point::class.java.simpleName.toLowerCase()
    }
}