package com.walk2gather.model.dto

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.walk2gather.model.db.Point

data class PointDTO(val timestamp: String, val position: LatLng) {

    companion object {
        fun initInstance(dataSnapshot: DataSnapshot): PointDTO {
            val tmp = (dataSnapshot.value as String).split(Point.DELIMITER_COORDINATES)
            return PointDTO(dataSnapshot.key!!, LatLng(tmp[0].toDouble(), tmp[1].toDouble()))
        }
    }
}