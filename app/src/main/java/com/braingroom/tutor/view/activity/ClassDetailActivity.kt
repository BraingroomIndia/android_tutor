package com.braingroom.tutor.view.activity

import android.app.Fragment
import android.os.Bundle
import com.braingroom.tutor.R
import com.braingroom.tutor.model.data.ClassData
import com.braingroom.tutor.model.resp.ClassDetailResp
import com.braingroom.tutor.utils.classBudleData
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.ClassDetailViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

/*
 * Created by godara on 28/01/18.
 */
class ClassDetailActivity : Activity() {
    lateinit var classData: ClassData


    var mapFragment: SupportMapFragment? = null

    override val vm: ClassDetailViewModel by lazy {
        classData = getIntentSerializable(classBudleData) as ClassData
        ClassDetailViewModel(helperFactory, classData)
    }
    override val layoutId: Int
        get() = R.layout.activity_class_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this::populateMarkers)
    }

    private fun populateMarkers(map: GoogleMap?) {

        var markerOption: MarkerOptions
        classData.classLocationList.forEach { location ->
            try {
                markerOption = MarkerOptions().position(location.latLng).title(location.locationArea)
                map?.addMarker(markerOption)
                map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location.latLng, 10.0f))
            } catch (e: Exception) {
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
