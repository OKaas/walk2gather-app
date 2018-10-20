package com.walk2gather.model.db

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class User(@get:PropertyName("username")
                @set:PropertyName("username")
                var username: String? = "",

                @get:PropertyName("password")
                @set:PropertyName("password")
                var password: String? = ""
){
    companion object Path {
        val PATH = User::class.java.simpleName.toLowerCase()
    }
}

