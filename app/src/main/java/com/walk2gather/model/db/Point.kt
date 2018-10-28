package com.walk2gather.model.db

data class Point (

    var uid: String,

    var points: Map<String, String>
){

    companion object Path {
        val PATH = Point::class.java.simpleName.toLowerCase()
    }
}