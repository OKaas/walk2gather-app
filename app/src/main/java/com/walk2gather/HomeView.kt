package com.walk2gather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

class HomeView : AppCompatActivity() {

    // Const
    // ===========================================================================================
    private val TAG = this::class.java.simpleName


    // Initializer
    //
    // ===========================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_view)


        // Get the Intent that started this activity and extract the string
        val loginName = intent.getStringExtra(LoginActivity.LOGIN_NAME)
        val loginPassword = intent.getStringExtra(LoginActivity.LOGIN_PASSWORD)

        Log.i(TAG, "$loginName > $loginPassword" )
    }

    // Toolbar
    //
    // ===========================================================================================
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        item.isChecked = true

        // Handle item selection
        return when (item.itemId) {
            R.id.main_menu_action_to_join -> {
                Log.i(TAG, "Clicked Add")
                item.isChecked = false
                true
            }
            R.id.main_menu_action_members -> {
                Log.i(TAG, "Clicked setting")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
