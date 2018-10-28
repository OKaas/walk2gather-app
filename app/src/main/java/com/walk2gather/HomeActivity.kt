package com.walk2gather

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.walk2gather.R.id.*
import com.walk2gather.R.menu.main_menu
import com.walk2gather.adapter.CustomAdapter
import com.walk2gather.model.db.Group
import com.walk2gather.model.ui.GroupItem
import kotlinx.android.synthetic.main.activity_home_view.*

// TODO VERY VERY BAD PRACTISE!!!
var UID_USER = ""
var UID_POINT = ""

class HomeActivity : AppCompatActivity() {

    // Const
    // ===========================================================================================
    private val TAG                     = this::class.java.simpleName

    // Data
    // ===========================================================================================
    private lateinit var database:      DatabaseReference
    private lateinit var auth:          FirebaseAuth

    private var modelGroup:             ArrayList<GroupItem>        = arrayListOf()

    // GUI
    // ===========================================================================================
    private lateinit var adapter:       CustomAdapter


    // Initializer
    //
    // ===========================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_view)

        Log.i(TAG, "onCreate")

        modelGroup.clear()
        adapter = CustomAdapter(modelGroup, applicationContext)

        homeView_listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val dataModel = modelGroup[position]

            Log.i(TAG, dataModel.toString())

            // save current point UID
            UID_POINT = dataModel.uidPoint

            startActivity(Intent(this@HomeActivity, MapsActivity::class.java))
        }

        // Prepare database connection
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")

        modelGroup.clear()

        database.child(Group.PATH).addListenerForSingleValueEvent( object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val groups = snapshot.children

                // fill Item holder
                groups.forEach {
                    val group = it.getValue(Group::class.java) as Group
                    modelGroup.add( GroupItem(group.name, group.ocucpancy, group.points!!.keys.first()) )
                }

                fillGroupList()
            }
        })
    }

    // UI
    //
    // ===========================================================================================
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        item.isChecked = true

        // Handle item selection
        return when (item.itemId) {
            main_menu_action_to_join -> {
                Log.i(TAG, "Clicked Add")
                startActivity(Intent(this@HomeActivity, MapsActivity::class.java))
                item.isChecked = false
                true
            }
            main_menu_action_members -> {
                Log.i(TAG, "Clicked setting")
                true
            }
            main_menu_action_create_group -> {
                Log.i(TAG, "Clicked create group")
                intentCreateGroup()
                true
            }
            main_menu_action_quit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Contributors
    // ===========================================================================================
    private fun fillGroupList(){
        homeView_listView.adapter = adapter
    }


    private fun intentCreateGroup(){
//        val intent = Intent(this, CreateGroupActivity::class.java).apply {
//            putExtra(USER_UID, uidUser)
//        }
//        startActivity(intent)
        startActivity(Intent(this@HomeActivity, CreateGroupActivity::class.java))
    }

}
