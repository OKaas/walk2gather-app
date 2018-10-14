package com.walk2gather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View


class LoginActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_view)
    }

    fun login(view: View ) {
        Log.d(TAG,"Clicking!")
    }

}
