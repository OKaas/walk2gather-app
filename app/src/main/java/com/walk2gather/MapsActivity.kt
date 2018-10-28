package com.walk2gather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.walk2gather.R.id.maps_menu_drawing
import com.walk2gather.R.menu.maps_menu
import com.walk2gather.model.db.Point
import com.walk2gather.model.dto.PointDTO
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    // CONST
    // ===========================================================================================
    private val TAG                                             = this::class.java.simpleName
    private val PADDING_ZOOM:               Int                 = 50

    // DATA
    // ===========================================================================================
    private lateinit var mMap:              GoogleMap
    private lateinit var database:          DatabaseReference
    private lateinit var auth:              FirebaseAuth
    private lateinit var point:             DatabaseReference
    private lateinit var latLngBuilder:     LatLngBounds.Builder

    private var mX:                         Double              = 0.0
    private var mY:                         Double              = 0.0

    private var mSizePoint:                 Int                 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        point = database.child(Point.PATH).child(UID_POINT).child(Point.PATH_COORDINATES)

        // initialize builder
        latLngBuilder = LatLngBounds.Builder()

        point.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(snapshot: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val points = snapshot.children
                Log.i(TAG, "Points changed!")

                mX = 0.0
                mY = 0.0
                mSizePoint = 0

                points.forEach {
                    Log.i(TAG, "TS: ${it.key} > value: ${it.value}")

                    val tmp = PointDTO.initInstance(it).position

                    latLngBuilder.include(tmp)

                    mX += tmp.latitude
                    mY += tmp.longitude
                    ++mSizePoint

                    mMap.addMarker(MarkerOptions().position(tmp))
                }

                when{
                    mSizePoint > 1 -> mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBuilder.build(), PADDING_ZOOM))
                    mSizePoint == 1 -> mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(mX / mSizePoint, mY / mSizePoint)))
                }

//                mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(mX / mSizePoint, mY / mSizePoint)))
            }
        })

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapClickListener { latLng ->
            Log.i(TAG, "$latLng.toString()")
            mMap.addMarker(MarkerOptions().position(latLng).title("Dount touch me!"))
            savePoint(Point.FORMAT_TIMESTAMP.format(Date()), latLng.latitude, latLng.longitude)
        }
    }

    // UI
    //
    // ===========================================================================================
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(maps_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Handle item selection
        return when (item.itemId) {
            maps_menu_drawing -> {
                Log.i(TAG, "Clicked stop drawing")

                if( item.isChecked ) {
                    item.isChecked = false
                    mMap.uiSettings.isScrollGesturesEnabled = false

                } else {
                    item.isChecked = true
                    mMap.uiSettings.isScrollGesturesEnabled = true
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun savePoint(timestamp: String, x: Double, y: Double){
        point.child(timestamp).setValue(Point.formatCoordinates(x, y))
    }
}
