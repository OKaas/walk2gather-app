package com.walk2gather.model.db

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Group(

    @get:PropertyName("uid")
    @set:PropertyName("uid")
    var uid: String,

    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String = "",

    @get:PropertyName("occupancy")
    @set:PropertyName("occupancy")
    var ocucpancy: Int = 0,

    @get:PropertyName("point")
    @set:PropertyName("point")
    var points: Map<String, Boolean>? = null,


    @get:PropertyName("member")
    @set:PropertyName("member")
    var member: Map<String, GroupMember>? = null

)  {

    companion object Path {
        val PATH = Group::class.java.simpleName.toLowerCase()
    }
}
