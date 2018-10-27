package com.walk2gather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.walk2gather.model.db.Point
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    // Const
    // ===========================================================================================
    private val TAG                         = this::class.java.simpleName

    // Data
    // ===========================================================================================
    private lateinit var database:          DatabaseReference
    private lateinit var auth:              FirebaseAuth
    private lateinit var point:             DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        database.child(Point.PATH).child(UID_POINT).child("points").addValueEventListener( object : ValueEventListener {
            override fun onCancelled(snapshot: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val points = snapshot.children
                Log.i(TAG, "Points changed!")


                points.forEach {
                    val point = it.getValue(Map::class.java) as Map<Float, Float>

                    canvas_view.mx = point.keys.first()
                    canvas_view.my = point.values.first()

                    canvas_view.invalidate()
                }
            }
        })

        canvas_view.setOnTouchListener { v: View, event: MotionEvent ->

            val x = event.x
            val y = event.y

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    canvas_view.mx = x
                    canvas_view.my = y
                    canvas_view.invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
//                moveTouch(x, y)
                    canvas_view.invalidate()
                }
                MotionEvent.ACTION_UP -> {
//                upTouch()
                    canvas_view.invalidate()
                }
            }
            true
        }

    }
}
