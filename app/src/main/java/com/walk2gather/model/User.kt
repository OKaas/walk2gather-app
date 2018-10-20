package com.walk2gather.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class User(@get:PropertyName("username")
                @set:PropertyName("username")
                var username: String? = "",

                @get:PropertyName("password")
                @set:PropertyName("password")
                var password: String? = "")