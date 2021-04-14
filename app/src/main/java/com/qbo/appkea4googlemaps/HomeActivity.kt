package com.qbo.appkea4googlemaps

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.qbo.appkea4googlemaps.databinding.ActivityHomeBinding
import java.io.IOException

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnvermapa.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if(verificarPermisoGPS()){
            try{
                startActivity(
                        Intent(
                                applicationContext,
                                MapsActivity::class.java
                        )
                )
            }catch (e : IOException){

            }
        }else{
            solicitarPermisoGPS()
        }
    }

    private fun solicitarPermisoGPS(){
        ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
        )
    }

    private fun verificarPermisoGPS() : Boolean{
        val result = ContextCompat.checkSelfPermission(
                applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        var exito = false
        if(result == PackageManager.PERMISSION_GRANTED) exito = true
        return exito

    }
}