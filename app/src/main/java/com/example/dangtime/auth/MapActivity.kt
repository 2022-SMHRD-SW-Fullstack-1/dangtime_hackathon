package com.example.dangtime.auth

import android.annotation.SuppressLint
import android.app.TaskStackBuilder.create
import android.content.Intent
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.dangtime.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HttpCoreContext.create

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val btnAutoLocation = findViewById<Button>(R.id.btnAutoLocation)

        btnAutoLocation.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val lat = intent.getStringExtra("lat").toString()
        val long = intent.getStringExtra("long").toString()
        val addr = intent.getStringExtra("addr").toString()

        // Add a marker in Sydney and move the camera
        val smhrd = LatLng(lat.toDouble(), long.toDouble())
        mMap.addMarker(
            MarkerOptions()
                .position(smhrd)
                .title(addr)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(smhrd))
        mMap.setMinZoomPreference(15.0f)
    }


//    @SuppressLint("MissingPermission")
//    fun updateLocation() {
//        /* 위치 정보를 요청할 locationRequest 생성하고 정확도와 주기를 설정 */
//        val locationRequest = LocationRequest.create()
//        locationRequest.run {
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            interval = 1000
//        }
//
//        locationCallback = object : LocationCallback() {  // 해당 주기마다 반환받을 locationCallback
//            override fun onLocationResult(p0: LocationResult) {
//                p0?.let {
//                    for(location in it.locations) {
//                        Log.d("Location", "${location.latitude} , ${location.longitude}")
//                        setLastLocation(location)
//                    }
//                }
//            }
//        }
//
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
//
//    }

}

