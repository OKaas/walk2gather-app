package com.walk2gather

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.walk2gather.model.db.User
import kotlinx.android.synthetic.main.activity_login_view.*



class LoginActivity : AppCompatActivity() {


    // Constants
    // ===========================================================================================
    private val TAG = this::class.java.simpleName
    private val DB_USERS_USERNAME = "username"
    companion object {
        const val LOGIN_NAME = "loginActivity_name"
        const val LOGIN_PASSWORD = "loginActivity_password"
    }

    // UI
    // ===========================================================================================
    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(this)
    }

    // Data
    // ===========================================================================================
    private lateinit var database:      DatabaseReference
    private lateinit var auth:          FirebaseAuth

    // Initializer
    //
    // ===========================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_view)

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()

        // Check auth on Activity start
//        auth.currentUser?.let {
//            onAuthSuccess(it)
//        }
    }

    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    fun onClickLogin(v: View){
        signIn()
    }

    fun onClickRegister(v: View){
        signUp()
    }

    private fun signIn() {
        Log.d(TAG, "signIn")
//        if (!validateForm()) {
//            return
//        }

        showProgressDialog()
        val username = login_textView_name.text.toString()
        val password = login_textView_password.text.toString()

        // TODO: Be sure have some indexes
        database.child(User.PATH).orderByChild(DB_USERS_USERNAME).equalTo(username).addListenerForSingleValueEvent( object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.i(TAG, p0.toString())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children

                users.forEach {
                    val user= it.getValue(User::class.java) as User
                    if(user.password.equals(password)){
                        onAuthSuccess(it.key!!)
                    } else {
                        Log.i(TAG, "Cannot login" +user.toString())
                    }
                }
            }
        })
    }

    private fun signUp() {
        Log.d(TAG, "signUp")

        val username = login_textView_name.text.toString()
        val password = login_textView_password.text.toString()

        showProgressDialog()

        database.child(User.PATH).orderByChild(DB_USERS_USERNAME).equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.i(TAG, "Not exists ...")


            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i(TAG, "Exists ...")

                if (snapshot.value == null) {
                    createUser(username, password)
                }
            }
        })
    }

    private fun createUser(userName: String, password: String){
        val ref = database.child(User.PATH).push()
        val user = User(ref.key!!, userName, password)

        ref.setValue(user).addOnCompleteListener(this) { task ->
            Log.i(TAG, "createUser:onComplete:" + task.isSuccessful)
            hideProgressDialog()
            if (task.isSuccessful) {
                onAuthSuccess(ref.key!!)
            } else {
                Toast.makeText(
                    baseContext, "Sign Up Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun onAuthSuccess(uidUser: String) {
        UID_USER = uidUser
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finish()
    }

    private fun usernameFromEmail(email: String): String {
        return if (email.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }

    private fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)
        database.child("users").child(userId).setValue(user)
    }

    // UI
    //
    // ===========================================================================================
    private fun showProgressDialog() {
        progressDialog.setMessage(getString(R.string.loading))
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    private fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }


}