package com.example.myandrostory.ui.map

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myandrostory.R
import com.example.myandrostory.data.Result
import com.example.myandrostory.databinding.ActivityMapsBinding
import com.example.myandrostory.ui.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@MapsActivity)
        val viewModel: MapViewModel by viewModels {
            factory
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getStoriesWithLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        viewStoryMarkers()
    }

    private fun viewStoryMarkers() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@MapsActivity)
        val viewModel: MapViewModel by viewModels {
            factory
        }

        viewModel.storyResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    val data = result.data?.listStory
                    data?.forEach { story ->
                        val lat = story.lat
                        val lon = story.lon
                        if (lat != null && lon != null) {
                            val latLng = LatLng(story.lat, story.lon)
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(latLng)
                                    .title(story.name)
                                    .snippet(story.description)
                            )
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                        }
                    }
                }

                is Result.Error -> {
                    Toast.makeText(
                        this@MapsActivity,
                        "Gagal memuat lokasi cerita", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}