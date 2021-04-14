package com.qbo.appkea4googlemaps

import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener,
        LocationListener
{

    private lateinit var mMap: GoogleMap
    private var lstLatLng = ArrayList<LatLng>()
    private lateinit var locationManager: LocationManager
    private val LOCATION_REFRESH_TIME: Long = 10000
    private val LOCATION_REFRESH_DISTANCE: Float = 10F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(this)
        mMap.setOnMarkerDragListener(this)
        // Add a marker in Sydney and move the camera
        val posicion = LatLng(-12.069339, -77.034360)
        mMap.addMarker(MarkerOptions()
                .position(posicion)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps))
                .title("Marcador QBO")
                .snippet("Kotlin Expert for Android")
        )
        mMap.isTrafficEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 16.0F))
        try {
            mMap.isMyLocationEnabled = true
            obtenerUbicacion()
        }catch (e: SecurityException){
            Toast.makeText(applicationContext,
                "Error activando la ubicación", Toast.LENGTH_LONG).show()
        }
    }

    private fun obtenerUbicacion(){
        try{
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE,
                this
            )
        }catch (ex: SecurityException){
            Toast.makeText(applicationContext,
            "Error mostrando la ubicación", Toast.LENGTH_LONG).show()
        }
    }

    override fun onMapClick(p0: LatLng?) {
        mMap.addMarker(MarkerOptions()
                .position(p0!!)
                //.draggable(true)
                .title("Nuevo Marcador"))
        lstLatLng.add(p0)
        //val poligono = PolygonOptions()
        val linea = PolylineOptions()
        linea.color(Color.RED)
        linea.width(5F)
        linea.addAll(lstLatLng)
        mMap.addPolyline(linea)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(p0!!))
    }

    override fun onMarkerDragStart(p0: Marker?) {
        p0!!.hideInfoWindow()
    }

    override fun onMarkerDrag(p0: Marker?) {
        var posicion = p0!!.position
        p0!!.snippet = posicion.latitude.toString() + " - " +
                posicion.longitude.toString()
        p0!!.showInfoWindow()
    }

    override fun onMarkerDragEnd(p0: Marker?) {

    }

    override fun onLocationChanged(location: Location?) {

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }
}