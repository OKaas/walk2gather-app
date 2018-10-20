package com.walk2gather.model.db

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import java.util.*

@IgnoreExtraProperties
data class Group(
    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String = "",

    @get:PropertyName("moderator")
    @set:PropertyName("moderator")
    var moderator: String = "",

    @get:PropertyName("occupancy")
    @set:PropertyName("occupancy")
    var ocucpancy: Int = 0,


    @get:PropertyName("points")
    @set:PropertyName("points")
    var points: Array<LatLng>? = null
){
    companion object Path {
        val PATH = Group::class.java.simpleName.toLowerCase()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Group

        if (name != other.name) return false
        if (moderator != other.moderator) return false
        if (ocucpancy != other.ocucpancy) return false
        if (!Arrays.equals(points, other.points)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + moderator.hashCode()
        result = 31 * result + ocucpancy
        result = 31 * result + Arrays.hashCode(points)
        return result
    }
}
