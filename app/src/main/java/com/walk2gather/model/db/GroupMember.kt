package com.walk2gather.model.db

data class GroupMember(

    var moderator: Boolean = false

) {

    companion object Path {
        val PATH = User::class.java.simpleName.toLowerCase()
    }
}