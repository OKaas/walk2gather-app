package com.walk2gather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.walk2gather.model.db.Point
import com.walk2gather.model.dto.PointDTO
import kotlinx.android.synthetic.main.activity_map.*
import java.text.SimpleDateFormat
import java.util.*

class MapActivity : AppCompatActivity() {

    // Const
    // ===========================================================================================
    private val TAG                         = this::class.java.simpleName
    private val FORMAT_TIMESTAMP            = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

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

        point = database.child(com.walk2gather.model.db.Point.PATH).child(UID_POINT).child(Point.PATH_COORDINATES)

        point.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(snapshot: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val points = snapshot.children
                Log.i(TAG, "Points changed!")

                points.forEach {
                    Log.i(TAG, "TS: ${it.key} > value: ${it.value}")

                    val pt = PointDTO.initInstance(it)

                    canvas_view.addPoint(pt.position.latitude, pt.position.longitude)
                }
                canvas_view.invalidate()
            }
        })

//        canvas_view.setOnTouchListener { v: View, event: MotionEvent ->
//
//            val x = event.x
//            val y = event.y
//
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    savePoint(FORMAT_TIMESTAMP.format(Date()), x, y)
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    canvas_view.invalidate()
//                }
//                MotionEvent.ACTION_UP -> {
//                    canvas_view.invalidate()
//                }
//            }
//            true
//        }

    }

    private fun savePoint(timestamp: String, x: Double, y: Double){
        point.child(timestamp).setValue(Point.formatCoordinates(x, y))
    }
}
