package com.walk2gather.model.db

data class UserGroup (

    var moderator: Boolean = false

){

    companion object Path {
        val PATH = Group::class.java.simpleName.toLowerCase()
    }
}
