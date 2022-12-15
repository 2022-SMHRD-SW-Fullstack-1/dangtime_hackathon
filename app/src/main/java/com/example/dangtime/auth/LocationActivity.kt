package com.example.dangtime.auth

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.dangtime.R
import com.google.android.gms.location.*
import java.util.*

class LocationActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val multiplePermissionsCode = 100
    var address = ArrayList<String>()
    var lat: Double = 0.0
    var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        val imgLocationBack = findViewById<ImageView>(R.id.imgLocationBack)
        imgLocationBack.setOnClickListener {
            finish()
        }
        val btnLocationSearch = findViewById<Button>(R.id.btnLocationSearch)
        btnLocationSearch.setOnClickListener {
            val intent = Intent(this@LocationActivity, SearchLocationActivity::class.java)
            startActivity(intent)
        }

        val btnLocationAuto = findViewById<Button>(R.id.btnLocationAuto)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnLocationAuto.setOnClickListener {
            checkLocationPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    var geocoder = Geocoder(this, Locale.KOREA)
                    if (location != null) {
                        val addrList =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        for (addr in addrList) {
                            val splitedAddr = addr.getAddressLine(0).split(" ")
                            address = splitedAddr as ArrayList<String>
                        }
                        Toast.makeText(this, "$address", Toast.LENGTH_SHORT).show()
                        lat = location.latitude
                        long = location.longitude

                        val intent = Intent(this, MapActivity::class.java)
                        intent.putExtra("lat", lat.toString())
                        intent.putExtra("long", long.toString())
//                        주소 동단위로 자르기
                        intent.putExtra("addr", address[address.size - 2].toString())
                        startActivity(intent)
                    }
                }
        } else {
            Toast.makeText(this, "위치권한이 없습니다..", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            multiplePermissionsCode -> {
                if (grantResults.isNotEmpty()) {
                    for ((i, permission) in permissions.withIndex()) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //권한 획득 실패시 동작
                            Toast.makeText(
                                this,
                                "The user has denied to $permission",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.i("TAG", "I can't work for you anymore then. ByeBye!")
                        } else {
                            var gpsGranted = true
                        }
                    }
                }
            }
        }
    }
}


