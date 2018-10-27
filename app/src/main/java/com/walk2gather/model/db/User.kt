package com.walk2gather.model.db

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class User(

    @get:PropertyName("uid")
    @set:PropertyName("uid")
    var uid: String = "",

    @get:PropertyName("username")
    @set:PropertyName("username")
    var username: String = "",

    @get:PropertyName("password")
    @set:PropertyName("password")
    var password: String = "",

    @get:PropertyName("group")
    @set:PropertyName("group")
    var group: Map<String, UserGroup>? = null

) {

    companion object Path {
        val PATH = User::class.java.simpleName.toLowerCase()
    }
}

