package com.walk2gather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import com.walk2gather.model.GroupItem
import com.walk2gather.adapter.CustomAdapter
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import android.view.View
import android.widget.AdapterView




class HomeActivity : AppCompatActivity() {

    // Const
    // ===========================================================================================
    private val TAG = this::class.java.simpleName


    private lateinit var listView : ListView

    private lateinit var adapter: CustomAdapter

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


        // Fill ListView data -> get data, create adapter for them

        var model = arrayListOf<GroupItem>()
        for (i in 1..20){
            model.add(GroupItem("Test $i", i))
        }

        adapter = CustomAdapter(model, applicationContext)

        listView = findViewById(R.id.homeView_listView)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val dataModel = model.get(position)

            Log.i(TAG, dataModel.toString())

//            Snackbar.make(
//                view,
//                dataModel.getName() + "\n" + dataModel.getType() + " API: " + dataModel.getVersion_number(),
//                Snackbar.LENGTH_LONG
//            )
//                .setAction("No action", null).show()
        }

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
