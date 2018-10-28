package com.walk2gather.model.db

import java.text.SimpleDateFormat

data class Point (

    var uid: String,

    var points: Map<String, String>
){

    companion object Path {
        val PATH = Point::class.java.simpleName.toLowerCase()
        const val PATH_COORDINATES = "points"
        const val DELIMITER_COORDINATES = "|"
        val FORMAT_TIMESTAMP = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        fun formatCoordinates(x: Double, y: Double): String {
            return "$x $DELIMITER_COORDINATES $y"
        }
    }
}