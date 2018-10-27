package com.walk2gather.model.db


data class Group(

    var uid: String = "",
    var name: String = "",
    var ocucpancy: Int = 0,
    var points: Map<String, Boolean>? = null,
    var member: Map<String, GroupMember>? = null

){

    companion object Path {
        val PATH = Group::class.java.simpleName.toLowerCase()
    }
}
