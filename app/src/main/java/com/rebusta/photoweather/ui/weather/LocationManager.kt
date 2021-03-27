package com.rebusta.photoweather.ui.weather

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender.SendIntentException
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

class LocationManager(
    private val activity: Activity, private val locationManagerListener: (Location) -> Unit
) {
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mSettingsClient: SettingsClient
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationSettingsRequest: LocationSettingsRequest
    private lateinit var mLocationCallback: LocationCallback

    private fun setupLocationService() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        mSettingsClient = LocationServices.getSettingsClient(activity)
        createLocationCallback()
        createLocationRequest()
        buildLocationSettingsRequest()
    }

    private fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        mLocationSettingsRequest = builder.build()
    }

    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationManagerListener.invoke(locationResult.lastLocation)
            }
        }
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval =
            UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.fastestInterval =
            FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
            .addOnSuccessListener(activity) { locationSettingsResponse: LocationSettingsResponse? ->
                Log.d("Location", "All location settings are satisfied.")
                mFusedLocationClient.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback,
                    Looper.myLooper()
                )
            }
            .addOnFailureListener(activity) { e: Exception ->
                val statusCode = (e as ApiException).statusCode
                handleStartLocationFailureCases(e as ResolvableApiException, statusCode)
            }
    }

    private fun handleStartLocationFailureCases(e: ResolvableApiException, statusCode: Int) {
        when (statusCode) {
            CommonStatusCodes.RESOLUTION_REQUIRED -> {
                Log.d(
                    "Location", "Location settings are not satisfied. Attempting to upgrade " +
                            "location settings "
                )
                try {
                    e.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS)
                } catch (sie: SendIntentException) {
                    Log.d("Location", "PendingIntent unable to execute request.")
                }
            }
            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                val errorMessage = "Location settings are inadequate, and cannot be " +
                        "fixed here. Fix in Settings."
                Log.e("Location", errorMessage)
            }
            else -> {
            }
        }
    }

    fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }

    companion object {
        const val REQUEST_CHECK_SETTINGS = 2
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }

    init {
        setupLocationService()
    }
}