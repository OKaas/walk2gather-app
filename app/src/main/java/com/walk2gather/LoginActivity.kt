package com.walk2gather

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

class LoginActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    companion object {
        const val LOGIN_NAME = "loginActivity_name"
        const val LOGIN_PASSWORD = "loginActivity_password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_view)
    }

    fun login(view: View) {
        Log.d(TAG, "Clicking!")

        val loginName = findViewById<EditText>(R.id.login_textView_name).text.toString()
        val loginPassowrd = findViewById<EditText>(R.id.login_textView_password).text.toString()
        val intent = Intent(this, HomeView::class.java).apply {
            putExtra(LOGIN_NAME, loginName)
            putExtra(LOGIN_PASSWORD, loginPassowrd)
        }
        startActivity(intent)
    }

}