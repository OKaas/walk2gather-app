package com.walk2gather

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.walk2gather.model.User
import kotlinx.android.synthetic.main.activity_login_view.*
import android.app.ProgressDialog
import android.support.annotation.VisibleForTesting
import android.widget.Toast
import com.google.firebase.database.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    private val DB_USERS = "users"
    private val DB_USERS_USERNAME = "username"

    companion object {
        const val LOGIN_NAME = "loginActivity_name"
        const val LOGIN_PASSWORD = "loginActivity_password"
    }

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(this)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_view)

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
    }

    fun onClickLogin(v: View){
        signIn()
    }

    fun onClickRegister(v: View){
        signUp()
    }

//    fun login(view: View) {
//        Log.d(TAG, "Clicking!")
//
//        val loginName = findViewById<EditText>(R.id.login_textView_name).text.toString()
//        val loginPassowrd = findViewById<EditText>(R.id.login_textView_password).text.toString()
//        val intent = Intent(this, HomeActivity::class.java).apply {
//            putExtra(LOGIN_NAME, loginName)
//            putExtra(LOGIN_PASSWORD, loginPassowrd)
//        }
//        startActivity(intent)
//    }

    private fun signIn() {
        Log.d(TAG, "signIn")
//        if (!validateForm()) {
//            return
//        }

        showProgressDialog()
        val username = login_textView_name.text.toString()
        val password = login_textView_password.text.toString()

        // TODO: Not nice to "download" all of the users
        database.child(DB_USERS).orderByChild(DB_USERS_USERNAME).equalTo(username).addListenerForSingleValueEvent( object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.i(TAG, p0.toString())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children

                users.forEach {
                    val user= it.getValue(User::class.java) as User
                    if(user.password.equals(password)){
                        onAuthSuccess(user)
                    } else {
                        Log.i(TAG, "Cannot login" +user.toString())
                    }
                }
            }

        })

//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                Log.d(TAG, "signIn:onComplete:" + task.isSuccessful)
////                hideProgressDialog()
//
//                if (task.isSuccessful) {
//                    onAuthSuccess(task.result.user)
//                } else {
//                    Toast.makeText(baseContext, "Sign In Failed",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
    }

    private fun signUp() {
        Log.d(TAG, "signUp")
//        if (!validateForm()) {
//            return
//        }

        showProgressDialog()

        val user = User(login_textView_name.text.toString(), login_textView_password.text.toString())

        database.child(DB_USERS).child(UUID.randomUUID().toString()).setValue(user)
            .addOnCompleteListener(this) { task ->
                Log.i(TAG, "createUser:onComplete:" + task.isSuccessful)
                hideProgressDialog()
                if (task.isSuccessful) {
                    onAuthSuccess(user)
                } else {
                    Toast.makeText(baseContext, "Sign Up Failed",
                        Toast.LENGTH_SHORT).show()
                }

        }

//
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                Log.d(TAG, "createUser:onComplete:" + task.isSuccessful)
////                hideProgressDialog()
//
//                if (task.isSuccessful) {
//                    onAuthSuccess(task.result.user)
//                } else {
//                    Toast.makeText(baseContext, "Sign Up Failed",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
    }

    private fun onAuthSuccess(user: User) {
//        val username = usernameFromEmail(user.email!!)

        // Write new user
//        writeNewUser(user.uid, username, user.email)

//        val intent = Intent(this, HomeActivity::class.java).apply {
//                        putExtra(LOGIN_NAME, username)
//        }
//        startActivity(intent)

        // Go to MainActivity
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

//    private fun validateForm(): Boolean {
//        var result = true
//        if (TextUtils.isEmpty(fieldEmail.text.toString())) {
//            fieldEmail.error = "Required"
//            result = false
//        } else {
//            fieldEmail.error = null
//        }
//
//        if (TextUtils.isEmpty(fieldPassword.text.toString())) {
//            fieldPassword.error = "Required"
//            result = false
//        } else {
//            fieldPassword.error = null
//        }
//
//        return result
//    }

    // [START basic_write]
    private fun writeNewUser(userId: String, name: String, email: String?) {
        val user = User(name, email)
        database.child("users").child(userId).setValue(user)
    }

    fun showProgressDialog() {
        progressDialog.setMessage(getString(R.string.loading))
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }


}