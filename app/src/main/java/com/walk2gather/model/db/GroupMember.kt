package com.walk2gather.model.db

import com.google.firebase.database.PropertyName

data class GroupMember(

    // uid of user
    var uidUser : String,

    @get:PropertyName("moderator")
    @set:PropertyName("moderator")
    var moderator: Boolean = false

) {

    companion object Path {
        val PATH = User::class.java.simpleName.toLowerCase()
    }
}