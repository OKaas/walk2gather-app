package com.walk2gather.model.db

data class User(
    var uid: String = "",
    var username: String = "",
    var password: String = "",
    var group: Map<String, UserGroup>? = null

) {

    companion object Path {
        val PATH = User::class.java.simpleName.toLowerCase()
    }
}

