package com.walk2gather.model.db

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class UserGroup (
    @get:PropertyName("moderator")
    @set:PropertyName("moderator")
    var moderator: Boolean? = false

){

    companion object Path {
        val PATH = Group::class.java.simpleName.toLowerCase()
    }
}
