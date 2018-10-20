package com.walk2gather

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.Tasks.call
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.walk2gather.model.db.Group
import kotlinx.android.synthetic.main.activity_create_group.*
import java.util.*

class CreateGroupActivity : AppCompatActivity() {

    // Constants
    // ===========================================================================================
    private val TAG = this::class.java.simpleName
    private val DB_GROUP_NAME = "name"

    // Data
    // ===========================================================================================
    private lateinit var database: DatabaseReference
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
        invokeCreateGroup(textView_nameGroup.text.toString())
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

    private fun ifGroupExists(nameGroup: String, call: (nameGroup: String) -> Unit){
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
                        createGroup(nameGroup)
                    }
                }
        })
    }

    private fun createGroup(nameGroup : String){
        val grp = Group(nameGroup, "test", 0, null )
        database.child(Group.PATH).child(UUID.randomUUID().toString()).setValue(grp)
            .addOnCompleteListener(this) { task ->
                Log.i(TAG, "invokeCreateGroup:onComplete: " + task.isSuccessful)

                if (task.isSuccessful) {
                    backToHome()
                } else {
                    // TODO some errors
                }
            }
    }

    private fun invokeCreateGroup(nameGroup: String) {
        // check if
        ifGroupExists(nameGroup, ::invokeCreateGroup)
    }
}
