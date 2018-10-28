package com.walk2gather

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.walk2gather.model.db.*
import kotlinx.android.synthetic.main.activity_create_group.*

class CreateGroupActivity : AppCompatActivity() {

    // Constants
    // ===========================================================================================
    private val TAG = this::class.java.simpleName
    private val DB_GROUP_NAME = "name"

    // Data
    // ===========================================================================================
    private lateinit var database:      DatabaseReference
    private lateinit var auth:          FirebaseAuth

    // Initializer
    //
    // ===========================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
    }

    // UI
    // ===========================================================================================
    fun onClickCreateGroup(v: View?){
        ifGroupExists(textView_nameGroup.text.toString())
    }

    fun onClickBack(v: View?){
        backToHome()
    }

    private fun backToHome(){
        startActivity(Intent(this@CreateGroupActivity, HomeActivity::class.java))
        finish()
    }

    // Contributors
    // ===========================================================================================

    private fun ifGroupExists(nameGroup: String) {
        database.child(Group.PATH).orderByChild(DB_GROUP_NAME).equalTo(nameGroup).
            addListenerForSingleValueEvent( object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.i(TAG, p0.toString())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val users = snapshot.children

                    var missing = true

                    for (user in users){
                        val group= user.value as Group

                        if(group.name == nameGroup){
                            missing = false
                            Log.i(TAG, "Group already exists" + group.name)
                        }
                    }

                    // check if missing
                    if (missing) {
                        createGroup(nameGroup, UID_USER)
                    }
                }
        })
    }

    private fun createGroup(nameGroup : String, uidUser : String) {

        val refGroup = database.child(Group.PATH).push()
        val refPoint = database.child(Point.PATH).push()
        val refUserGroup = database.child(User.PATH).child(uidUser).child(UserGroup.PATH).child(refGroup.key!!)

        val grpMember = GroupMember(true)
        val point = Point(refPoint.key!!, mapOf())
        val grp = Group(refGroup.key!!, nameGroup, 0, mapOf(refPoint.key!! to true), mapOf(uidUser to grpMember))
        val userGroup = UserGroup(true)

        // Save new group
        refGroup.setValue(grp)

        // Save points
        refPoint.setValue(point)

        // Push new group to user
        refUserGroup.setValue(userGroup).addOnCompleteListener(this) { task ->
            Log.i(TAG, "invokeCreateGroupMember:onComplete: " + task.isSuccessful)

            if (task.isSuccessful) {
                backToHome()
            } else {
                // TODO some errors
            }
        }
    }
}
