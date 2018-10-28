package com.walk2gather.model.db

data class Point (

    var uid: String,

    var points: Map<String, String>
){

    companion object Path {
        val PATH = Point::class.java.simpleName.toLowerCase()
        const val PATH_COORDINATES = "points"
        const val DELIMITER_COORDINATES = "|"

        fun formatCoordinates(x: Float, y: Float): String {
            return "$x $DELIMITER_COORDINATES $y"
        }
    }
}