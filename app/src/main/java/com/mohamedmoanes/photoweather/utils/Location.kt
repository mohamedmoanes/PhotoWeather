package com.mohamedmoanes.photoweather.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.location.LocationSettingsStatusCodes

fun getLocation(context: Context, locationCallback: LocationCallback) {
    if (ActivityCompat.checkSelfPermission(
            context,
            ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(
            context,
            ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        val permission = arrayOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
        )
        ActivityCompat.requestPermissions((context as Activity?)!!, permission, 0)
    }
    // Create the location request to start receiving updates
    val mLocationRequest = LocationRequest()
    mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    /* 10 secs */
    val updateInterval = 120 * 1000.toLong()
    mLocationRequest.interval = updateInterval
    /* 2 sec */
    val fastestInterval: Long = 60 * 1000.toLong()
    mLocationRequest.fastestInterval = fastestInterval
    // Create LocationSettingsRequest object using location request
    val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
    builder.addLocationRequest(mLocationRequest)
    val result: Task<LocationSettingsResponse> =
        LocationServices.getSettingsClient(context).checkLocationSettings(builder.build())
    result.addOnCompleteListener { task ->
        try {
            val response: LocationSettingsResponse = task.getResult(ApiException::class.java)!!
            // All location settings are satisfied. The client can initialize location
// requests here.
        } catch (exception: ApiException) {
            when (exception.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->  // Location settings are not satisfied. But could be fixed by showing the
// user a dialog.
                    try { // Cast to a resolvable exception.
                        val resolvable: ResolvableApiException =
                            exception as ResolvableApiException
                        // Show the dialog by calling startResolutionForResult(),
// and check the result in onActivityResult().
                        resolvable.startResolutionForResult(
                            context as Activity?
                            ,
                            LocationRequest.PRIORITY_HIGH_ACCURACY
                        )
                    } catch (e: SendIntentException) { // Ignore the error.
                    } catch (e: ClassCastException) { // Ignore, should be an impossible error.
                    }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                }
                else -> {
                }
            }
        }
    }
    // new Google API SDK v11 uses getFusedLocationProviderClient(this)
    getFusedLocationProviderClient(context).requestLocationUpdates(
        mLocationRequest,
        locationCallback,
        Looper.myLooper()
    )
}

fun removeLocationListener(context: Context, locationCallback: LocationCallback) {
    getFusedLocationProviderClient(context).removeLocationUpdates(locationCallback)
}

