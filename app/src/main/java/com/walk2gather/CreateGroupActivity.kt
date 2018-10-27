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
import java.util.*

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
                        val group= user.getValue(Group::class.java) as Group

                        if(group.name == nameGroup){
                            missing = false
                            Log.i(TAG, "Group already exists" + group.name)
                        }
                    }

                    // check if missing
                    if (missing) {
                        createGroup(nameGroup, "test")
                    }
                }
        })
    }

    private fun createGroup(nameGroup : String, uidUser : String){

        val grpMember = GroupMember(uidUser, true)

        val point = Point(UUID.randomUUID().toString(), arrayListOf(1.0f, 2.0f, 3.0f))

        val grp = Group(UUID.randomUUID().toString(), nameGroup, 0, mapOf(point.uid to true), mapOf(grpMember.uidUser to grpMember))

        // Save new group
        database.child(Group.PATH).child(grp.uid).setValue(grp)
            .addOnCompleteListener(this) { task ->
                Log.i(TAG, "invokeCreateGroup:onComplete: " + task.isSuccessful)

                if (task.isSuccessful) {
//                    backToHome()
                } else {
                    // TODO some errors
                }
            }

        // Save points
        database.child(Point.PATH).child(point.uid).setValue(point)
            .addOnCompleteListener(this) { task ->
                Log.i(TAG, "invokeCreateGroupMember:onComplete: " + task.isSuccessful)

                if (task.isSuccessful) {
//                    backToHome()
                } else {
                    // TODO some errors
                }
            }

        // Update user to become moderator
//        database.child(User.PATH).child(uidUser).updateChildren(mapOf("/group"))
//            .addOnCompleteListener(this) { task ->
//                Log.i(TAG, "invokeCreateGroupMember:onComplete: " + task.isSuccessful)
//
//                if (task.isSuccessful) {
////                    backToHome()
//                } else {
//                    // TODO some errors
//                }
//            }
    }
}
