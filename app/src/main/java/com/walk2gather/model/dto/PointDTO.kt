package com.walk2gather.model.dto

import com.google.firebase.database.DataSnapshot
import com.walk2gather.model.db.Point

data class PointDTO(val timestamp: String, val x: Float, val y: Float) {

    companion object {
        fun initInstance(dataSnapshot: DataSnapshot): PointDTO {
            val tmp = (dataSnapshot.value as String).split(Point.DELIMITER_COORDINATES)
            return PointDTO(dataSnapshot.key!!, tmp[0].toFloat(), tmp[1].toFloat())
        }
    }
}